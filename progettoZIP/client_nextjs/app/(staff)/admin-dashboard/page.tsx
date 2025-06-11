"use client"

import React from "react"
import Link from "next/link"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

export default function AdminDashboardPage() {
  return (
    <main className="min-h-screen w-2/3 mx-auto bg-background p-8">
      <h1 className="text-4xl font-bold text-center mb-12">Strumenti Admin</h1>
      <div className="flex flex-col md:flex-row justify-center items-stretch gap-8">
        {/* Gestisci Commessi */}
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Gestisci Commessi</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-center">
            <p className="mb-6 text-center">
              Aggiungi e modifica i profili dei commessi.
            </p>
            <Link href="/admin-dashboard/gestisci-commessi">
              <Button>Vai a Gestisci Commessi</Button>
            </Link>
          </CardContent>
        </Card>

        {/* Crea Newsletter */}
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Crea Newsletter</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-center">
            <p className="mb-6 text-center">Scrivi newsletter agli iscritti.</p>
            <Link href="/admin-dashboard/crea-newsletter">
              <Button>Vai a Crea Newsletter</Button>
            </Link>
          </CardContent>
        </Card>
      </div>
    </main>
  )
}
