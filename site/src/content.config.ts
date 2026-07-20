import { defineCollection, z } from 'astro:content';
import { docsLoader } from '@astrojs/starlight/loaders';
import { docsSchema } from '@astrojs/starlight/schema';

const leccionExtras = z.object({
  etapa: z.number().optional(),
  etapaTitulo: z.string().optional(),
  leccion: z.number().optional(),
  objetivo: z.string().optional(),
  prerrequisitos: z.array(z.string()).default([]),
  version_proyecto: z.string().optional(),
});

const docs = defineCollection({
  loader: docsLoader(),
  schema: docsSchema({ extend: leccionExtras }),
});

export const collections = { docs };
