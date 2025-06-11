"use client"

import React, { ReactNode, useEffect, useState } from "react"
import { useRouter } from "next/navigation"

interface Props {
  children: ReactNode
}

export default function AdminLayout({ children }: Props) {
  const router = useRouter()
  const [authorized, setAuthorized] = useState<boolean | null>(null)

  useEffect(() => {
    const auth = localStorage.getItem("authenticated")
    const userJson = localStorage.getItem("user")

    // Se non loggato, vai al login
    if (auth !== "true" || !userJson) {
      router.replace("/login")
      return
    }

    let user: any
    try {
      user = JSON.parse(userJson)
      if (typeof user === "string") {
        user = JSON.parse(user)
      }
    } catch (e) {
      console.error("[AdminLayout] JSON.parse fallito", e)
      router.replace("/login")
      return
    }

    const roleRaw = typeof user.role === "string" ? user.role : ""
    const role = roleRaw.trim().toLowerCase()

    // Permesso solo per amministratore
    if (role !== "amministratore") {
      router.replace("/")
      return
    }

    setAuthorized(true)
  }, [router])

  // Fino al check, non renderizzare nulla
  if (authorized !== true) {
    return null
  }

  return <>{children}</>
}
