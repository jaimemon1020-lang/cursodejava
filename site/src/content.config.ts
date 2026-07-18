import { defineCollection } from 'astro:content';
import { glob } from 'astro/loaders';
import { leccionSchema } from './content/schema';

const lecciones = defineCollection({
  loader: glob({ pattern: '**/*.mdx', base: './src/content/lecciones' }),
  schema: leccionSchema,
});

export const collections = { lecciones };
