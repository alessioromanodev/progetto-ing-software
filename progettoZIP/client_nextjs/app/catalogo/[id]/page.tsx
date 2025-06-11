"use client"

import React, { useEffect, useState } from "react"
import { useParams, useRouter } from "next/navigation"

import { Button } from "@/components/ui/button"
import { Card } from "@/components/ui/card"
import { Input } from "@/components/ui/input"

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

export default function FumettoDetail() {
  const { id } = useParams()
  const router = useRouter()
  const [fumetto, setFumetto] = useState<Fumetto | null>(null)
  const [loading, setLoading] = useState(true)
  const [quantity, setQuantity] = useState<number>(1)

  useEffect(() => {
    if (!id) return
    fetch(`http://localhost:8080/fumetti/${id}`)
      .then((res) => res.json())
      .then((data: Fumetto) => setFumetto(data))
      .catch((err) => console.error("Errore fetch fumetto:", err))
      .finally(() => setLoading(false))
  }, [id])

  const handleAcquista = () => {
    if (!fumetto) return
    const saved = localStorage.getItem("cart")
    const cart = saved ? JSON.parse(saved) : []
    cart.push({
      ...fumetto,
      quantity,
    })
    localStorage.setItem("cart", JSON.stringify(cart))
    router.push("/carrello")
  }

  if (loading) {
    return <p className="text-center p-8">Caricamento...</p>
  }
  if (!fumetto) {
    return <p className="text-center p-8 text-red-500">Fumetto non trovato.</p>
  }

  return (
    <div className="h-[75vh] w-2/3 mx-auto mt-20">
      <Card className="flex flex-col lg:flex-row h-full w-full">
        <img
          src={fumetto.immagineCopertina}
          alt={fumetto.titolo}
          className="w-full lg:w-1/2 h-full object-cover rounded-t-lg lg:rounded-l-lg lg:rounded-tr-none"
        />
        <div className="w-full lg:w-1/2 p-6 flex flex-col justify-between h-full">
          <div>
            <h1 className="text-5xl font-bold mb-2">{fumetto.titolo}</h1>
            <p className="text-lg text-muted-foreground mb-4">
              {fumetto.autore} • {fumetto.nomeSerie} {fumetto.annoSerie}
            </p>
            <p className="mb-6 text-justify">{fumetto.descrizione}</p>
          </div>
          <div className="space-y-6">
            <div className="flex justify-between items-center">
              <span className="text-3xl font-semibold">
                € {fumetto.prezzo.toFixed(2)}
              </span>
              <span className="text-base text-muted-foreground">
                Disponibili: {fumetto.quantitaDisponibile}
              </span>
            </div>
            <div className="flex items-center space-x-4">
              <Input
                type="number"
                min={1}
                max={fumetto.quantitaDisponibile}
                value={quantity}
                onChange={(e) =>
                  setQuantity(
                    Math.max(
                      1,
                      Math.min(
                        fumetto.quantitaDisponibile,
                        parseInt(e.target.value) || 1
                      )
                    )
                  )
                }
                className="w-20"
              />
              <Button onClick={handleAcquista} className="px-10 py-4">
                Acquista
              </Button>
            </div>
          </div>
        </div>
      </Card>
    </div>
  )
}
