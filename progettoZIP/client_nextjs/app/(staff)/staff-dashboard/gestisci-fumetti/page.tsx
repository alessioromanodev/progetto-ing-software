"use client"

import React, { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { useForm } from "react-hook-form"

import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog"
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"

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

export default function GestisciFumettiPage() {
  const [fumetti, setFumetti] = useState<Fumetto[]>([])
  const [selected, setSelected] = useState<Fumetto | null>(null)
  const [isOpen, setIsOpen] = useState(false)
  const router = useRouter()

  const form = useForm<Fumetto>({
    defaultValues: {
      id: 0,
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

  useEffect(() => {
    if (selected) form.reset(selected)
  }, [selected, form])

  useEffect(() => {
    fetch("http://localhost:8080/fumetti")
      .then((res) => res.json())
      .then((data: Fumetto[]) => setFumetti(data))
      .catch((err) => console.error("Errore fetch fumetti:", err))
  }, [])

  const onRowClick = (f: Fumetto) => {
    setSelected(f)
    setIsOpen(true)
  }

  const handleUpdate = async (values: Fumetto) => {
    const { id, ...payload } = values
    try {
      const res = await fetch(`http://localhost:8080/fumetti/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      })
      if (res.ok) {
        const updated = await res.json()
        setFumetti((prev) => prev.map((f) => (f.id === id ? updated : f)))
        setIsOpen(false)
      } else {
        console.error("Errore update fumetto", res.statusText)
      }
    } catch (e) {
      console.error("Network error:", e)
    }
  }

  return (
    <div className="min-h-screen p-8 bg-background">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Gestisci Fumetti</h1>
        <Button
          onClick={() =>
            router.push("/staff-dashboard/gestisci-fumetti/aggiungi-fumetto")
          }
        >
          +
        </Button>
      </div>
      <Table>
        <TableCaption>Elenco dei fumetti</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Titolo</TableHead>
            <TableHead>Volume</TableHead>
            <TableHead>Autore</TableHead>
            <TableHead>Prezzo (€)</TableHead>
            <TableHead>Disponibilità</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {fumetti.map((f, idx) => (
            <TableRow
              key={f.id}
              className="cursor-pointer hover:bg-muted"
              onClick={() => onRowClick(f)}
            >
              <TableCell>{f.id}</TableCell>
              <TableCell>{f.nomeSerie}</TableCell>
              <TableCell>{f.numeroVolume}</TableCell>
              <TableCell>{f.autore}</TableCell>
              <TableCell>
                €{typeof f.prezzo === "number" ? f.prezzo.toFixed(2) : "0.00"}
              </TableCell>
              <TableCell>{f.quantitaDisponibile}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent className="max-w-3xl">
          <DialogHeader>
            <DialogTitle>Modifica Fumetto</DialogTitle>
            <DialogDescription>
              Aggiorna i dettagli del fumetto selezionato.
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(handleUpdate)}
              className="space-y-4"
            >
              <div className="flex flex-col md:flex-row md:space-x-4">
                <FormField
                  control={form.control}
                  name="nomeSerie"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Nome Serie</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="annoSerie"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Anno Serie</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <div className="flex flex-col md:flex-row md:space-x-4">
                <FormField
                  control={form.control}
                  name="autore"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Autore</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="numeroVolume"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Numero Volume</FormLabel>
                      <FormControl>
                        <Input type="number" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <FormField
                control={form.control}
                name="titolo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Titolo</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <div className="flex flex-col md:flex-row md:space-x-4">
                <FormField
                  control={form.control}
                  name="genere"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Genere</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="casaEditrice"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Casa Editrice</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <FormField
                control={form.control}
                name="immagineCopertina"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>URL Immagine Copertina</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="descrizione"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Descrizione</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <div className="flex flex-col md:flex-row md:space-x-4">
                <FormField
                  control={form.control}
                  name="prezzo"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Prezzo (€)</FormLabel>
                      <FormControl>
                        <Input type="number" step="0.01" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="quantitaDisponibile"
                  render={({ field }) => (
                    <FormItem className="flex-1">
                      <FormLabel>Quantità Disponibile</FormLabel>
                      <FormControl>
                        <Input type="number" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <DialogFooter>
                <DialogClose asChild>
                  <Button variant="ghost">Annulla</Button>
                </DialogClose>
                <Button type="submit">Salva</Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </div>
  )
}
