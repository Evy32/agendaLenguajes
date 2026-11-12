# Agenda Records Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add in-memory student record actions and a minimalist dark table to the Swing agenda form.

**Architecture:** Keep the `DefaultTableModel` as the visible in-memory record state inside `Formulario`. Wire the four existing buttons to validation, lookup, deletion, and field clearing; style the table and inputs with Swing colors and borders.

**Tech Stack:** Java Swing, `DefaultTableModel`.

**Spec:** `docs/superpowers/specs/2026-11-03-agenda-registros-design.md`

## Global Constraints

- No external dependencies or CSS.
- Records remain in memory and disappear when the window closes.
- Use cédula as the unique lookup and deletion key.
- Keep the dark frosted-glass appearance as a Swing simulation.

## Review Focus

- Empty input: insertion reports required fields and does not add a row.
- Duplicate cédula: insertion reports the duplicate and keeps existing data.
- Missing cédula: search and deletion report no match without changing rows.
- Successful lookup: fields receive the matching row's values.
- Successful deletion: only the matching row is removed.

---

### Task 1: Add table, actions, and dark styling

**Files:**
- Modify: `src/Formulario.java`

**Interfaces:**
- Uses the existing text fields and buttons.
- Adds one non-editable table model with columns Cédula, Nombre, Teléfono, Carrera.

- [x] Add the styled table section and update window sizing.
- [x] Connect Insertar, Buscar, Eliminar, and Limpiar to the specified in-memory behavior.
- [x] Review the source for consistent colors, field mapping, and duplicate handling.

**Execution note:** Final self-review completed. Automated tests and compilation were not run.
