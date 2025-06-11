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

interface Utente {
  id: number
  nome: string
  cognome: string
  username: string
  role: string
  email: string
  indirizzo: string
  password: string
}

export default function GestisciCommessiPage() {
  const router = useRouter()
  const [commessi, setCommessi] = useState<Utente[]>([])
  const [selected, setSelected] = useState<Utente | null>(null)
  const [isOpen, setIsOpen] = useState(false)

  const form = useForm<Utente>({
    defaultValues: {
      id: 0,
      nome: "",
      cognome: "",
      username: "",
      role: "commesso",
      email: "",
      indirizzo: "",
      password: "",
    },
  })

  useEffect(() => {
    fetch("http://localhost:8080/utenti")
      .then((res) => res.json())
      .then((data: Utente[]) => {
        setCommessi(data.filter((u) => u.role === "commesso"))
      })
      .catch((err) => console.error("Errore fetch utenti:", err))
  }, [])

  useEffect(() => {
    if (selected) form.reset(selected)
  }, [selected, form])

  const onRowClick = (u: Utente) => {
    setSelected(u)
    setIsOpen(true)
  }

  const handleUpdate = async (values: Utente) => {
    const { id, ...payload } = values
    try {
      const res = await fetch(`http://localhost:8080/utenti/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...payload, role: "commesso" }),
      })
      if (res.ok) {
        const updated = await res.json()
        setCommessi((prev) => prev.map((u) => (u.id === id ? updated : u)))
        setIsOpen(false)
      } else {
        console.error("Errore update commesso", res.statusText)
      }
    } catch (e) {
      console.error("Network error:", e)
    }
  }

  return (
    <main className="min-h-screen p-8 bg-background">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Gestisci Commessi</h1>
        <Button
          onClick={() =>
            router.push("/admin-dashboard/gestisci-commessi/aggiungi-commessi")
          }
        >
          +
        </Button>
      </div>
      <h1 className="text-3xl font-bold mb-6 text-center">Gestisci Commessi</h1>
      <Table>
        <TableCaption>Elenco Commessi</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Nome</TableHead>
            <TableHead>Cognome</TableHead>
            <TableHead>Username</TableHead>
            <TableHead>Email</TableHead>
            <TableHead>Indirizzo</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {commessi.map((u) => (
            <TableRow
              key={u.id}
              className="cursor-pointer hover:bg-muted"
              onClick={() => onRowClick(u)}
            >
              <TableCell>{u.id}</TableCell>
              <TableCell>{u.nome}</TableCell>
              <TableCell>{u.cognome}</TableCell>
              <TableCell>{u.username}</TableCell>
              <TableCell>{u.email}</TableCell>
              <TableCell>{u.indirizzo}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent className="max-w-lg">
          <DialogHeader>
            <DialogTitle>Modifica Commesso</DialogTitle>
            <DialogDescription>Aggiorna i dati del commesso.</DialogDescription>
          </DialogHeader>
          {selected && (
            <Form {...form}>
              <form
                onSubmit={form.handleSubmit(handleUpdate)}
                className="space-y-4"
              >
                <div className="flex flex-col md:flex-row md:space-x-4">
                  <FormField
                    control={form.control}
                    name="nome"
                    render={({ field }) => (
                      <FormItem className="flex-1">
                        <FormLabel>Nome</FormLabel>
                        <FormControl>
                          <Input {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="cognome"
                    render={({ field }) => (
                      <FormItem className="flex-1">
                        <FormLabel>Cognome</FormLabel>
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
                  name="username"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Username</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Email</FormLabel>
                      <FormControl>
                        <Input type="email" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="indirizzo"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Indirizzo</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="password"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Password</FormLabel>
                      <FormControl>
                        <Input type="password" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <DialogFooter>
                  <DialogClose asChild>
                    <Button variant="ghost">Annulla</Button>
                  </DialogClose>
                  <Button type="submit">Salva</Button>
                </DialogFooter>
              </form>
            </Form>
          )}
        </DialogContent>
      </Dialog>
    </main>
  )
}
