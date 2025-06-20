"use client"

import React, { useEffect, useState } from "react"
import { reviews } from "@/data/reviews"

import { Cover } from "@/components/cover"
import { FocusCards } from "@/components/focus-cards"
import { HeroParallax } from "@/components/hero-parallax"
import { InfiniteMovingCards } from "@/components/infinite-moving-cards"

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
      .then((data: Fumetto[]) => {
        setUserProducts(data)
      })
      .catch((err) => console.error("Errore fetch fumetti:", err))
  }, [])

  const allCardsForHero = userProducts.map((f) => ({
    title: f.titolo,
    link: "#",
    thumbnail: f.immagineCopertina,
  }))

  const firstSixCards = userProducts.slice(0, 6).map((f) => ({
    id: f.id,
    title: f.titolo,
    src: f.immagineCopertina,
  }))

  return (
    <div className="space-y-0">
      <HeroParallax products={allCardsForHero} />
      <div className="flex flex-col items-center w-full space-y-24">
        <div className="flex flex-col items-center">
          <Cover className="text-8xl font-extrabold">
            Scopri i Top Sellers!
          </Cover>
        </div>

        <div className="w-full">
          <FocusCards cards={firstSixCards} />
        </div>

        <div className="flex flex-col items-center mt-48">
          <Cover className="text-8xl font-extrabold">Cosa dicono di noi</Cover>
        </div>

        <div className="w-full">
          <InfiniteMovingCards
            items={reviews}
            direction="left"
            speed="normal"
            className="mx-auto"
          />
        </div>
      </div>
    </div>
  )
}
