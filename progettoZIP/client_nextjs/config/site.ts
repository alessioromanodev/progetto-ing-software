export type SiteConfig = typeof siteConfig

export const siteConfig = {
  name: "Fumetteria ZIP",
  description:
    "La tua fumetteria di fiducia!",
  mainNav: [
    {
      title: "Home",
      href: "/",
    },
    {
      title: "Catalogo",
      href: "/catalogo"
    },
    {
      title: "Ordini",
      href: "/ordini"
    }
  ],
  links: {
    github: "https://github.com/alessioromanodev/progetto-ing-software",
  },
}
