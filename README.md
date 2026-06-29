# TaskTrack

TaskTrack merupakan aplikasi manajemen tugas berbasis Android yang dirancang untuk membantu pengguna mencatat, mengelola, dan memantau tugas sehari-hari. Aplikasi ini menyediakan fitur penambahan, pengubahan, penghapusan, pencarian, serta penyaringan tugas berdasarkan status. Dikembangkan menggunakan Kotlin pada Android Studio dan memanfaatkan SQLite sebagai database lokal.

## Fitur
```
- CRUD Tugas        : tambah, lihat, edit, dan hapus tugas
- Detail Tugas      : tampilan lengkap per tugas
- Pencarian         : filter tugas berdasarkan kata kunci
- Filter Status     : Belum Dikerjakan / Sedang Dikerjakan / Selesai
- Validasi Input    : notifikasi Toast jika ada field kosong
- Dialog Konfirmasi : konfirmasi sebelum hapus data
- DatePickerDialog  : pemilihan deadline via kalender
- Offline-first     : semua data tersimpan lokal, tanpa internet
```
## Tech Stack

| Komponen | Teknologi |
|---|---|
| Bahasa | Kotlin |
| IDE | Android Studio |
| Database | SQLite (via `DatabaseHelper`) |
| UI | XML Layout, RecyclerView, CardView |
| Navigasi | Intent + Activity |

## Build & Download

APK debug tersedia di:

```
build/outputs/apk/debug/
```

## Struktur Proyek

```
com.kelompok6.tasktrack/
├── adapter/        # TaskAdapter (RecyclerView)
├── database/       # DatabaseHelper (SQLite CRUD)
├── model/          # Task (data class)
├── MainActivity
├── AddEditTaskActivity
└── DetailTaskActivity
```

## Database

Satu tabel `tasks` pada `tasktrack.db`:

| Kolom | Tipe | Keterangan |
|---|---|---|
| `id` | INTEGER | Primary Key, Auto Increment |
| `mata_kuliah` | TEXT | Nama mata kuliah |
| `judul` | TEXT | Judul tugas |
| `deskripsi` | TEXT | Deskripsi tugas |
| `deadline` | TEXT | Batas waktu |
| `status` | TEXT | Belum / Sedang / Selesai |

## Disusun Oleh

| Nama | NIM |
|---|---|
| Afdhal Tsany Nurrizki | 2430511078 |
| Hildan Faris Kamaludin | 2430511059 |
| Muhamad Dikrulloh | 2430511076 |
| Muhammad Syahdan A | 2430511071 |
