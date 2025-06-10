"use client";

import React from "react";
import { useRouter } from "next/navigation";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Icons } from "@/components/icons";

export default function ThankYouPage() {
  const router = useRouter();
  return (
    <div className="min-h-screen flex items-center justify-center bg-background">
      <Card className="max-w-md w-full mx-4">
        <CardContent className="p-8 text-center">
          <Icons.checkCircle className="mx-auto mb-4 h-12 w-12 text-green-500" />
          <h1 className="text-2xl font-bold mb-2">Grazie per il tuo ordine!</h1>
          <p className="mb-6 text-muted-foreground">
            La tua richiesta è stata ricevuta con successo. Il tuo ordine sarà elaborato a breve.
          </p>
          <Button onClick={() => router.push("/")} className="w-full">
            Torna alla Home
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}
