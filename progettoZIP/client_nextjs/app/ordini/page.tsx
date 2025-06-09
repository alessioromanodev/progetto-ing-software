"use client"

import React, { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import { Card, CardContent } from "@/components/ui/card"
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
  TableCaption,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"

interface RigheOrdine {
  id: number
  idOrdine: number
  idFumetto: number
  quantita: number
  prezzoUnitario: number
}

interface Ordine {
  id: number
  dataOrdine: string
  importoTotale: number
  metodoConsegna: string
  statoOrdine: string
  qrCode: string
  righeOrdine: RigheOrdine[]
}

export default function OrdiniPage() {
  const router = useRouter()
  const [orders, setOrders] = useState<Ordine[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    // Controllo autenticazione
    const auth = localStorage.getItem("authenticated")
    const userJson = localStorage.getItem("user")
    if (auth !== "true" || !userJson) {
      router.push("/login")
      return
    }

    const user = JSON.parse(userJson)
    // 1. Recupera elenco ordini dell'utente
    fetch(`http://localhost:8080/ordini?userId=${user.id}`)
      .then((res) => {
        if (!res.ok) throw new Error("Errore nel recupero ordini")
        return res.json()
      })
      .then((list: { id: number }[]) => {
        // 2. Per ciascun ordine, recupera i dettagli completi
        return Promise.all(
          list.map((o) =>
            fetch(`http://localhost:8080/ordini/${o.id}`)
              .then((res) => {
                if (!res.ok) throw new Error(`Errore nel recupero dettaglio ordine ${o.id}`)
                return res.json()
              })
          )
        )
      })
      .then((detailed: Ordine[]) => setOrders(detailed))
      .catch((err) => {
        console.error(err)
        setOrders([])
      })
      .finally(() => setLoading(false))
  }, [router])

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <p>Caricamento ordini…</p>
      </div>
    )
  }

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-4xl">
        <CardContent className="p-8">
          <h1 className="text-3xl font-bold mb-6 text-center">I tuoi ordini</h1>

          {orders.length === 0 ? (
            <div className="text-center space-y-4">
              <p className="text-lg font-medium">Ops! Non hai ancora ordini, guarda il nostro catalogo.</p>
              <Button onClick={() => router.push("/catalogo")}>Vai al catalogo</Button>
            </div>
          ) : (
            <Table>
              <TableCaption>Elenco dei tuoi ordini</TableCaption>
              <TableHeader>
                <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Data ordine</TableHead>
                  <TableHead>Totale (€)</TableHead>
                  <TableHead>Stato</TableHead>
                  <TableHead>Consegna</TableHead>
                  <TableHead># Articoli</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {orders.map((o) => (
                  <TableRow key={o.id}>
                    <TableCell>{o.id}</TableCell>
                    <TableCell>{new Date(o.dataOrdine).toLocaleString()}</TableCell>
                    <TableCell>€{o.importoTotale.toFixed(2)}</TableCell>
                    <TableCell>{o.statoOrdine}</TableCell>
                    <TableCell>{o.metodoConsegna}</TableCell>
                    <TableCell>{o.righeOrdine.length}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}

          <div className="mt-6 text-center">
            <Button onClick={() => router.push("/")}>Torna alla home</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  )
}