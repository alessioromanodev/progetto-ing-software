"use client"

import React, { useState } from "react"
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

interface LoginFormValues {
  email: string
  password: string
}

export default function LoginPage() {
  const router = useRouter()
  const [error, setError] = useState<string | null>(null)

  const form = useForm<LoginFormValues>({
    defaultValues: {
      email: "",
      password: "",
    },
  })

  const onSubmit = async (data: LoginFormValues) => {
    try {
      const res = await fetch("http://localhost:8080/utenti/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          email: data.email,
          password: data.password,
        }),
      })

      if (res.ok) {
        const user = await res.json()
        localStorage.setItem("authenticated", "true")
        localStorage.setItem("user", JSON.stringify(user))
        router.push("/")
      } else {
        setError("Email o password non validi")
      }
    } catch (e) {
      console.error("Network error:", e)
      setError("Errore di rete. Riprova più tardi.")
    }
  }

  return (
    <div className="min-h-screen flex items-start justify-center pt-16 bg-background">
      <Card className="w-full max-w-md">
        <CardContent className="p-8">
          <h1 className="text-3xl font-bold mb-8 text-center">Accedi</h1>

          {error && <p className="mb-4 text-red-600 text-center">{error}</p>}

          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Email</FormLabel>
                    <FormControl>
                      <Input
                        type="email"
                        placeholder="Inserisci la tua email"
                        {...field}
                      />
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
                      <Input
                        type="password"
                        placeholder="Inserisci la tua password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full py-4 text-lg">
                Accedi
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  )
}
