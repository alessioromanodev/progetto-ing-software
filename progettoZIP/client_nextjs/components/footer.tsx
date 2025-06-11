"use client"

import React from "react"
import Link from "next/link"

import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Icons } from "@/components/icons"

export function SiteFooter() {
  return (
    <footer className="bg-gray-50 dark:bg-gray-900 text-gray-700 dark:text-gray-200 mt-36 py-12">
      <div className="container w-2/3 gap-40 mx-auto flex flex-col sm:flex-row justify-center items-start px-4 space-y-8 sm:space-y-0 sm:space-x-8">
        {/* Quick Links */}
        <div className="flex-1">
          <h3 className="text-lg font-semibold mb-4">Link Utili</h3>
          <ul className="space-y-2 text-sm">
            <li>
              <Link href="/" className="hover:text-blue-600">
                Home
              </Link>
            </li>
            <li>
              <Link href="/catalogo" className="hover:text-blue-600">
                Catalogo
              </Link>
            </li>
            <li>
              <Link href="/staff-dashboard" className="hover:text-blue-600">
                Area Staff
              </Link>
            </li>
            <li>
              <Link href="/admin-dashboard" className="hover:text-blue-600">
                Area Admin
              </Link>
            </li>
          </ul>
        </div>

        {/* About section */}
        <div className="flex-1">
          <h3 className="text-lg font-semibold mb-4">FumetteriaZIP</h3>
          <p className="text-sm">
            La tua fumetteria di fiducia online. Scopri i migliori titoli,
            ordina comodamente da casa e ricevi velocemente.
          </p>
          <div className="flex space-x-3 mt-4">
            <Link href="#" className="hover:text-blue-600">
              <Icons.twitter className="w-5 h-5" />
            </Link>
          </div>
        </div>

        {/* Contact */}
        <div className="flex-1">
          <h3 className="text-lg font-semibold mb-4">Contatti</h3>
          <address className="not-italic text-sm space-y-2">
            <p>Via Nicolangelo Portopisani 123</p>
            <p>00100 Napoli, Italia</p>
            <p>Email: info@fumetteriazip.it</p>
            <p>Tel: +39 01 1234567</p>
          </address>
        </div>
      </div>
      <div className="mt-8 border-t border-gray-200 dark:border-gray-700 pt-4 text-center text-xs text-gray-500">
        © 2025 FumetteriaZIP. Tutti i diritti riservati.
      </div>
    </footer>
  )
}
