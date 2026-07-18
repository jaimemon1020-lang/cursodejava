import { z } from 'astro/zod';

export const leccionSchema = z.object({
  etapa: z.number().int().min(0),
  etapaTitulo: z.string().min(1),
  leccion: z.number().int().min(1),
  titulo: z.string().min(1),
  objetivo: z.string().min(1),
  prerrequisitos: z.array(z.string()),
  version_proyecto: z.string().min(1),
});

export type Leccion = z.infer<typeof leccionSchema>;
