"use client"

import React, { useEffect, useState } from "react"
import Link from "next/link"

import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

interface Fumetto {
  id: number
  nomeSerie: string
  annoSerie: string
  autore: string
  numeroVolume: number
  titolo: string
  genere: string
  casaEditrice: string
  immagineCopertina: string
  descrizione: string
  prezzo: number
  quantitaDisponibile: number
}

export default function Page() {
  const [userProducts, setUserProducts] = useState<Fumetto[]>([])

  useEffect(() => {
    fetch("http://localhost:8080/fumetti")
      .then((res) => res.json())
      .then((data: Fumetto[]) => setUserProducts(data))
      .catch((err) => console.error("Errore fetch fumetti:", err))
  }, [])

  return (
    <div className="w-[80%] mx-auto flex flex-wrap justify-center gap-8 p-6">
      {userProducts.map((fumetto) => (
        <Card key={fumetto.id} className="w-full sm:w-auto max-w-sm mx-auto">
          <CardHeader className="flex flex-col items-center space-y-4">
            <img
              src={fumetto.immagineCopertina}
              alt={fumetto.nomeSerie}
              className="w-full h-48 object-cover rounded-md"
            />
            <CardTitle className="text-center">{fumetto.nomeSerie}</CardTitle>
            <CardDescription className="text-center">
              {fumetto.descrizione}
            </CardDescription>
            <CardAction className="mt-2">
              <Link
                href={`/catalogo/${fumetto.id}`}
                className="hover:underline"
              >
                Visualizza
              </Link>
            </CardAction>
          </CardHeader>
          <CardContent className="space-y-2">
            <p className="text-lg font-semibold text-center">
              € {fumetto.prezzo.toFixed(2)}
            </p>
            <p className="text-sm text-muted-foreground text-center">
              Disponibili: {fumetto.quantitaDisponibile}
            </p>
          </CardContent>
          <CardFooter className="justify-center">
            <small className="text-xs text-gray-500 text-center">
              {fumetto.genere} • Vol. {fumetto.numeroVolume}
            </small>
          </CardFooter>
        </Card>
      ))}
    </div>
  )
}
