"use client"

import React from "react"
import { useForm } from "react-hook-form"
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Card, CardContent } from "@/components/ui/card"
import { useRouter } from "next/navigation"

interface SignUpFormValues {
  nome: string
  cognome: string
  username: string
  email: string
  password: string
  indirizzo: string
}

export default function SignUpPage() {
  const router = useRouter()
  const form = useForm<SignUpFormValues>({
    defaultValues: {
      nome: "",
      cognome: "",
      username: "",
      email: "",
      password: "",
      indirizzo: "",
    },
  })

  const onSubmit = async (data: SignUpFormValues) => {
    try {
      const res = await fetch('http://localhost:8080/utenti', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nome: data.nome,
          cognome: data.cognome,
          username: data.username,
          email: data.email,
          password: data.password,
          indirizzo: data.indirizzo,
          registrato: true,
          role: 'USER',
        }),
      })
      if (res.ok) {
        router.push('/')
      } else {
        console.error('Errore durante la registrazione')
      }
    } catch (error) {
      console.error('Network error:', error)
    }
  }

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-xl">
        <CardContent className="p-8">
          <h1 className="text-3xl font-bold mb-8 text-center">Registrazione</h1>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="nome"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome</FormLabel>
                    <FormControl>
                      <Input placeholder="Inserisci il tuo nome" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="cognome"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Cognome</FormLabel>
                    <FormControl>
                      <Input placeholder="Inserisci il tuo cognome" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Username</FormLabel>
                    <FormControl>
                      <Input placeholder="Scegli un username" {...field} />
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
                      <Input type="email" placeholder="Inserisci la tua email" {...field} />
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
                      <Input type="password" placeholder="Scegli una password" {...field} />
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
                      <Input placeholder="Inserisci il tuo indirizzo" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type="submit" className="w-full py-4 text-lg">
                Registrati
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  )
}