"use client"

import React from "react"
import { useRouter } from "next/navigation"
import { useForm } from "react-hook-form"

import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"

interface FumettoForm {
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

export default function NuovoFumettoPage() {
  const router = useRouter()
  const form = useForm<FumettoForm>({
    defaultValues: {
      nomeSerie: "",
      annoSerie: "",
      autore: "",
      numeroVolume: 1,
      titolo: "",
      genere: "",
      casaEditrice: "",
      immagineCopertina: "",
      descrizione: "",
      prezzo: 0,
      quantitaDisponibile: 0,
    },
  })

  const onSubmit = async (values: FumettoForm) => {
    try {
      const res = await fetch("http://localhost:8080/fumetti", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(values),
      })
      if (res.ok) {
        router.push("/staff-dashboard/gestisci-fumetti")
      } else {
        console.error("Errore creazione fumetto", res.statusText)
      }
    } catch (error) {
      console.error("Network error:", error)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-background p-8">
      <Card className="w-full max-w-2xl">
        <CardContent>
          <h1 className="text-2xl font-bold mb-6 text-center">
            Aggiungi Nuovo Fumetto
          </h1>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <FormField
                  name="nomeSerie"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome Serie</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="annoSerie"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Anno Serie</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="autore"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Autore</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="numeroVolume"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Numero Volume</FormLabel>
                      <FormControl>
                        <Input type="number" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="titolo"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem className="md:col-span-2">
                      <FormLabel>Titolo</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="genere"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Genere</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="casaEditrice"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Casa Editrice</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="immagineCopertina"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem className="md:col-span-2">
                      <FormLabel>URL Immagine Copertina</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="descrizione"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem className="md:col-span-2">
                      <FormLabel>Descrizione</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="prezzo"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Prezzo (€)</FormLabel>
                      <FormControl>
                        <Input type="number" step="0.01" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  name="quantitaDisponibile"
                  control={form.control}
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Quantità Disponibile</FormLabel>
                      <FormControl>
                        <Input type="number" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <Button type="submit" className="w-full py-4 text-lg mt-4">
                Aggiungi Fumetto
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  )
}
