"use client"

import React, { useEffect, useState } from "react"
import { useRouter } from "next/navigation"

import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

interface CartItem {
  id: number
  titolo: string
  prezzo: number
  immagineCopertina?: string
  quantity: number
}

export default function CarrelloPage() {
  const router = useRouter()
  const [cart, setCart] = useState<CartItem[]>([])
  const [purchaseType, setPurchaseType] = useState<string>(
    "Acquisto in negozio"
  )
  const [loading, setLoading] = useState<boolean>(false)

  useEffect(() => {
    // Controllo autenticazione
    const auth = localStorage.getItem("authenticated")
    const userJson = localStorage.getItem("user")
    if (auth !== "true" || !userJson) {
      router.push("/login")
      return
    }

    // Carica carrello
    const saved = localStorage.getItem("cart")
    if (saved) {
      setCart(JSON.parse(saved))
    }
  }, [router])

  const totale = cart.reduce(
    (sum, item) => sum + item.prezzo * item.quantity,
    0
  )

  // Genera un codice random di 20 caratteri alfanumerici
  const generateQrCode = () => {
    const chars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    let code = ""
    for (let i = 0; i < 20; i++) {
      code += chars.charAt(Math.floor(Math.random() * chars.length))
    }
    return code
  }

  const handleOrdina = async () => {
    if (cart.length === 0) return
    setLoading(true)
    try {
      const qrCode = generateQrCode()
      const userJson = localStorage.getItem("user")
      const user = userJson ? JSON.parse(userJson) : null
      const idUtente = user?.id ?? 1

      // Prepara il body per la creazione dell'ordine
      const orderBody = {
        dataOrdine: new Date().toISOString().split(".")[0],
        importoTotale: Number(totale.toFixed(2)),
        metodoConsegna: purchaseType,
        statoOrdine: "In lavorazione",
        qrCode,
        idUtente,
        righeOrdine: cart.map((item) => ({
          idFumetto: item.id,
          quantita: item.quantity,
          prezzoUnitario: item.prezzo,
        })),
      }

      // Crea l'ordine
      const orderRes = await fetch("http://localhost:8080/ordini", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(orderBody),
      })
      if (!orderRes.ok) throw new Error("Errore creazione ordine")
      const nuovoOrdine = await orderRes.json()

      // Crea il pagamento
      const paymentBody = {
        dataPagamento: nuovoOrdine.dataOrdine,
        importo: nuovoOrdine.importoTotale,
        metodo: purchaseType,
        scontoApplicato: false,
        idOrdine: nuovoOrdine.id,
      }
      const payRes = await fetch("http://localhost:8080/pagamenti", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paymentBody),
      })
      if (!payRes.ok) throw new Error("Errore creazione pagamento")

      // Crea la consegna
      const consegnaBody = {
        dataRichiesta: nuovoOrdine.dataOrdine,
        dataConsegna: nuovoOrdine.dataOrdine,
        stato: "In Lavorazione",
        idOrdine: nuovoOrdine.id,
      }
      const consRes = await fetch("http://localhost:8080/consegne", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(consegnaBody),
      })
      if (!consRes.ok) throw new Error("Errore creazione consegna")

      localStorage.removeItem("cart")
      router.push(`/staff-dashboard`)
    } catch (error) {
      console.error(error)
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-4xl">
        <CardContent className="p-8 space-y-6">
          <h1 className="text-3xl font-bold text-center">Il tuo Carrello</h1>

          {cart.length > 0 && (
            <div className="flex items-center space-x-4">
              <span>Tipo acquisto:</span>
              <Select value={purchaseType} onValueChange={setPurchaseType}>
                <SelectTrigger className="w-48">
                  <SelectValue placeholder="Seleziona tipo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="Acquisto in negozio">
                    Acquisto in negozio
                  </SelectItem>
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
                    <TableCell>
                      €{(item.prezzo * item.quantity).toFixed(2)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}

          {cart.length > 0 && (
            <div className="mt-6 text-right space-y-4">
              <p className="text-xl font-semibold">
                Totale: €{totale.toFixed(2)}
              </p>
              <Button
                onClick={handleOrdina}
                className="w-full py-4 text-lg"
                disabled={loading}
              >
                {loading ? "Sto creando ordine..." : "Ordina"}
              </Button>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
