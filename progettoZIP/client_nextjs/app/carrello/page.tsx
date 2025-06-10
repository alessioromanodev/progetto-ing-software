"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
  TableCaption,
} from "@/components/ui/table";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";

interface CartItem {
  id: number;
  titolo: string;
  prezzo: number;
  immagineCopertina?: string;
  quantity: number;
}

export default function CarrelloPage() {
  const router = useRouter();
  const [cart, setCart] = useState<CartItem[]>([]);
  const [shippingMethod, setShippingMethod] = useState<string>("Ritiro in negozio");
  const [loading, setLoading] = useState<boolean>(false);
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

  useEffect(() => {
    // Controllo autenticazione
    const auth = localStorage.getItem("authenticated");
    const userJson = localStorage.getItem("user");
    if (auth !== "true" || !userJson) {
      router.push("/login");
      return;
    }
    setIsLoggedIn(true);

    // Carica carrello
    const saved = localStorage.getItem("cart");
    if (saved) {
      setCart(JSON.parse(saved));
    }
  }, [router]);

  const totale = cart.reduce((sum, item) => sum + item.prezzo * item.quantity, 0);
  const discountedTotal = isLoggedIn ? Number((totale * 0.9).toFixed(2)) : totale;

  // Genera un codice random di 20 caratteri alfanumerici
  const generateQrCode = () => {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let code = '';
    for (let i = 0; i < 20; i++) {
      code += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return code;
  };

  const handleOrdina = async () => {
    if (cart.length === 0) return;
    setLoading(true);
    try {
      const qrCode = generateQrCode();
      const userIdStored = localStorage.getItem("user");
      const user = userIdStored ? JSON.parse(userIdStored) : null;
      const idUtente = user?.id ?? 1;

      // Prepara il body per la creazione dell'ordine
      const orderBody = {
        dataOrdine: new Date().toISOString().split(".")[0],
        importoTotale: Number(totale.toFixed(2)),
        metodoConsegna: shippingMethod,
        statoOrdine: "In lavorazione",
        qrCode,
        idUtente,
        righeOrdine: cart.map((item) => ({
          idFumetto: item.id,
          quantita: item.quantity,
          prezzoUnitario: item.prezzo,
        })),
      };

      // Crea l'ordine
      const orderRes = await fetch("http://localhost:8080/ordini", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(orderBody),
      });
      if (!orderRes.ok) throw new Error("Errore creazione ordine");
      const nuovoOrdine = await orderRes.json();

      // Prepara il body per la creazione del pagamento
      const paymentBody = {
        dataPagamento: nuovoOrdine.dataOrdine,
        importo: isLoggedIn ? discountedTotal : nuovoOrdine.importoTotale,
        metodo: "Carta di Credito",
        scontoApplicato: isLoggedIn,
        idOrdine: nuovoOrdine.id,
      };

      // Crea il pagamento
      const payRes = await fetch("http://localhost:8080/pagamenti", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paymentBody),
      });
      if (!payRes.ok) throw new Error("Errore creazione pagamento");

      // Prepara il body per la creazione della consegna
      const consegnaBody = {
        dataRichiesta: nuovoOrdine.dataOrdine,
        // data di consegna impostata uguale a richiesta per demo
        dataConsegna: nuovoOrdine.dataOrdine,
        stato: "In Lavorazione",
        idOrdine: nuovoOrdine.id,
      };

      // Crea la consegna
      const consRes = await fetch("http://localhost:8080/consegne", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(consegnaBody),
      });
      if (!consRes.ok) throw new Error("Errore creazione consegna");

      // Pulisci carrello e reindirizza alla conferma
      localStorage.removeItem("cart");
      router.push(`/thank-you`);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-4xl">
        <CardContent className="p-8 space-y-6">
          <h1 className="text-3xl font-bold text-center">Il tuo Carrello</h1>

          {cart.length > 0 && (
            <div className="flex items-center space-x-4">
              <span>Spedizione:</span>
              <Select value={shippingMethod} onValueChange={setShippingMethod}>
                <SelectTrigger className="w-48">
                  <SelectValue placeholder="Seleziona metodo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="Ritiro in negozio">Ritiro in negozio</SelectItem>
                  <SelectItem value="Consegna a domicilio">Consegna a domicilio</SelectItem>
                </SelectContent>
              </Select>
            </div>
          )}

          {cart.length === 0 ? (
            <p className="text-center">Il carrello è vuoto.</p>
          ) : (
            <Table>
              <TableCaption>Riepilogo degli articoli nel carrello</TableCaption>
              <TableHeader>
                <TableRow>
                  <TableHead>Fumetto</TableHead>
                  <TableHead>Prezzo unitario (€)</TableHead>
                  <TableHead>Quantità</TableHead>
                  <TableHead>Subtotale (€)</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {cart.map((item) => (
                  <TableRow key={item.id}>
                    <TableCell className="flex items-center space-x-2">
                      {item.immagineCopertina && (
                        <img
                          src={item.immagineCopertina}
                          alt={item.titolo}
                          className="w-12 h-12 object-cover rounded"
                        />
                      )}
                      <span>{item.titolo}</span>
                    </TableCell>
                    <TableCell>€{item.prezzo.toFixed(2)}</TableCell>
                    <TableCell>{item.quantity}</TableCell>
                    <TableCell>€{(item.prezzo * item.quantity).toFixed(2)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}

          {cart.length > 0 && (
            <div className="mt-6 space-y-4">
              <div className="text-right">
                <p className="text-xl font-semibold">Totale senza sconto: €{totale.toFixed(2)}</p>
                {isLoggedIn && (
                  <p className="text-lg text-green-600">
                    Sconto per i clienti registrati - 10%: €{discountedTotal.toFixed(2)}
                  </p>
                )}
              </div>

              {/* Sezione pagamento estetica */}
              <div className="space-y-2">
                <h2 className="text-2xl font-medium">Dati di Pagamento</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <Input placeholder="Numero carta di credito" maxLength={19} />
                  <Input placeholder="MM/AA" maxLength={5} />
                  <Input placeholder="CVC" maxLength={4} />
                  <Input placeholder="Intestatario carta" />
                </div>
              </div>

              <Button onClick={handleOrdina} className="w-full py-4 text-lg" disabled={loading}>
                {loading ? 'Sto creando ordine...' : 'Ordina'}
              </Button>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
