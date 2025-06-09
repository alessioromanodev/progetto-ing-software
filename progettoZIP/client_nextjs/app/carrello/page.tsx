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

  useEffect(() => {
    const saved = localStorage.getItem("cart");
    if (saved) {
      setCart(JSON.parse(saved));
    }
  }, []);

  const totale = cart.reduce((sum, item) => sum + item.prezzo * item.quantity, 0);

  const handleOrdina = () => {
    // Logica ordine: chiamata API o redirect
    // Per ora redirect a pagina di conferma ordine
    router.push("/conferma-ordine");
  };

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-4xl">
        <CardContent className="p-8">
          <h1 className="text-3xl font-bold mb-6 text-center">Il tuo Carrello</h1>

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
            <div className="mt-6 text-right space-y-4">
              <p className="text-xl font-semibold">Totale: €{totale.toFixed(2)}</p>
              <Button onClick={handleOrdina} className="w-full py-4 text-lg">
                Ordina
              </Button>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
