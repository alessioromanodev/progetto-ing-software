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
import { Textarea } from "@/components/ui/textarea"

interface NewsletterForm {
  titolo: string
  descrizione: string
}

export default function CreaNewsletterPage() {
  const router = useRouter()
  const form = useForm<NewsletterForm>({
    defaultValues: {
      titolo: "",
      descrizione: "",
    },
  })

  const onSubmit = async (values: NewsletterForm) => {
    try {
      const payload = {
        titolo: values.titolo,
        descrizione: values.descrizione,
        dataCreazione: new Date().toISOString().split(".")[0],
      }
      const res = await fetch("http://localhost:8080/newsletter", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      })
      if (res.ok) {
        router.push("/admin-dashboard")
      } else {
        console.error("Errore creazione newsletter", res.statusText)
      }
    } catch (e) {
      console.error("Network error:", e)
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-background p-8">
      <Card className="w-full py-8 max-w-2xl">
        <CardContent>
          <h1 className="text-2xl font-bold mb-6 text-center">
            Crea Newsletter
          </h1>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
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

              <FormField
                control={form.control}
                name="descrizione"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Descrizione</FormLabel>
                    <FormControl>
                      <Textarea rows={6} {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="w-full py-4 text-lg">
                Crea Newsletter
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  )
}
