"use client"

import React from "react"
import Link from "next/link"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"

export default function StaffDashboardPage() {
  return (
    <main className="min-h-screen w-2/3 mx-auto bg-background p-8">
      <h1 className="text-4xl font-bold text-center mb-12">Staff Dashboard</h1>
      <div className="flex flex-col md:flex-row justify-center items-stretch gap-8">
        {/* Registra Vendita */}
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Registra Vendita</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-center">
            <p className="mb-6 text-center">
              Registra una nuova vendita manualmente nel sistema.
            </p>
            <Link href="/staff-dashboard/registra-vendita">
              <Button>Vai a Registra Vendita</Button>
            </Link>
          </CardContent>
        </Card>

        {/* Gestisci Fumetti */}
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Gestisci Fumetti</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-center">
            <p className="mb-6 text-center">
              Aggiungi o modifica fumetti dal catalogo.
            </p>
            <Link href="/staff-dashboard/gestisci-fumetti">
              <Button>Vai a Gestisci Fumetti</Button>
            </Link>
          </CardContent>
        </Card>

        {/* Ritiro in negozio */}
        <Card className="flex-1">
          <CardHeader>
            <CardTitle>Ritiro in negozio</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col items-center">
            <p className="mb-6 text-center">
              Gestisci le richieste di ritiro in negozio.
            </p>
            <Link href="/staff-dashboard/ritiro-in-negozio">
              <Button>Vai a Ritiro</Button>
            </Link>
          </CardContent>
        </Card>
      </div>
    </main>
  )
}
