"use client"

import React, { useEffect, useState } from "react"

import { Card, CardContent } from "@/components/ui/card"
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

interface Ordine {
  id: number
  dataOrdine: string
  qrCode: string
  metodoConsegna: string
}

export default function RitiroInNegozioPage() {
  const [ordini, setOrdini] = useState<Ordine[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch("http://localhost:8080/ordini")
      .then((res) => res.json())
      .then((data: Ordine[]) => {
        // Filtra solo quelli con metodoConsegna == "Ritiro in negozio"
        const ritiri = data.filter(
          (o) => o.metodoConsegna === "Ritiro in negozio"
        )
        setOrdini(ritiri)
      })
      .catch((err) => console.error("Errore fetch ordini:", err))
      .finally(() => setLoading(false))
  }, [])

  if (loading) {
    return <p className="text-center p-8">Caricamento ordini...</p>
  }

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-4xl">
        <CardContent className="p-8">
          <h1 className="text-3xl font-bold mb-6 text-center">
            Ordini - Ritiro in Negozio
          </h1>
          {ordini.length === 0 ? (
            <p className="text-center">
              Nessun ordine con ritiro in negozio trovato.
            </p>
          ) : (
            <Table>
              <TableCaption>Elenco ordini (Ritiro in negozio)</TableCaption>
              <TableHeader>
                <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Data Ordine</TableHead>
                  <TableHead>QR Code</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {ordini.map((o) => (
                  <TableRow key={o.id}>
                    <TableCell>{o.id}</TableCell>
                    <TableCell>
                      {new Date(o.dataOrdine).toLocaleString()}
                    </TableCell>
                    <TableCell>{o.qrCode}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
