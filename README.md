# 📰 NewsDaily — Android News App

A production-ready Android news application built with modern Android development best practices.

---

## 🏗️ Tech Stack

| Layer | Technology |
|---|---|
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt |
| **Local DB** | Room |
| **Networking** | Retrofit 2 + OkHttp 3 |
| **Navigation** | Navigation Compose |
| **Async** | Kotlin Coroutines + Flow |
| **Image Loading** | Coil |
| **Offline Sync** | Offline-first Repository pattern |

---

## 📁 Project Structure

```
app/src/main/java/com/newsapp/
├── data/
│   ├── local/
│   │   ├── dao/          ArticleDao.kt
│   │   ├── entity/       ArticleEntity.kt
│   │   └── NewsDatabase.kt
│   ├── remote/
│   │   ├── api/          NewsApiService.kt
│   │   └── dto/          NewsResponseDto.kt
│   └── repository/
│       ├── Mappers.kt
│       └── NewsRepositoryImpl.kt
├── di/
│   └── AppModule.kt       Hilt DI bindings
├── domain/
│   ├── model/             Article.kt
│   └── usecase/           NewsUseCases.kt, NewsRepository.kt
├── presentation/
│   ├── navigation/        NavGraph.kt
│   ├── theme/             Theme.kt
│   └── ui/
│       ├── components/    NewsComponents.kt (shared widgets)
│       ├── home/          HomeScreen + HomeViewModel
│       │                  SearchScreen + SearchViewModel
│       ├── detail/        DetailScreen + DetailViewModel
│       └── bookmark/      BookmarkScreen + BookmarkViewModel
├── util/
│   ├── Resource.kt        Sealed class for UI state
│   ├── NetworkUtils.kt    Connectivity checker
│   └── DateUtils.kt       Time formatting
├── MainActivity.kt
└── NewsApplication.kt
```

---

## 🚀 Setup

### 1. Get a free API key
Go to **https://newsapi.org** → Sign up → Copy your API key.

### 2. Add your API key
Open `app/build.gradle.kts` and replace:
```kotlin
buildConfigField("String", "NEWS_API_KEY", "\"YOUR_NEWS_API_KEY_HERE\"")
```
with your actual key:
```kotlin
buildConfigField("String", "NEWS_API_KEY", "\"abc123yourkeyhere\"")
```

### 3. Build & Run
```bash
./gradlew assembleDebug
# or open in Android Studio and click Run
```

---

## ✨ Features

### 📱 Screens
- **Home** — Top headlines with category filter chips (General, Technology, Business, Sports, Entertainment, Health, Science)
- **Search** — Debounced real-time search (500ms) across remote + local DB
- **Detail** — Full article view with open-in-browser and bookmark actions
- **Bookmarks** — Offline-accessible saved articles

### 🔄 Offline-First Data Sync
```
getTopHeadlines() flow:
  1. Emit Loading
  2. Emit cached data from Room immediately (if any)
  3. If online AND (cache empty OR stale >30min): fetch from API
  4. Save fresh data to Room
  5. Emit fresh data
  6. On network error: silently serve cached data
```

### 🏛️ Architecture: MVVM + Clean
```
UI (Compose) → ViewModel → UseCase → Repository → [API + Room DB]
                ↑ StateFlow
```

---

## 🔑 Key Implementation Details

### Offline-First Repository
`NewsRepositoryImpl` uses a **cache-then-network** strategy:
- Always serves cached data first (zero latency)
- Refreshes in background if cache is stale (>30 min)
- Shows offline banner on HomeScreen when network fails

### Room Schema
```sql
TABLE articles (
  url TEXT PRIMARY KEY,
  title TEXT NOT NULL,
  sourceName TEXT,
  description TEXT,
  urlToImage TEXT,
  publishedAt TEXT,
  content TEXT,
  category TEXT DEFAULT 'general',
  isBookmarked INTEGER DEFAULT 0,
  cachedAt INTEGER            -- used for cache expiry
)
```

### Navigation Graph
```
HomeScreen  ──┐
SearchScreen ─┼──► DetailScreen (receives URL via NavArgs)
BookmarkScreen┘
```
Bottom navigation with `saveState = true` and `restoreState = true` for proper backstack.

### Coroutines Usage
- `viewModelScope.launch` — bookmark toggles, one-shot operations
- `flow { }` — offline-first data streams in Repository
- `StateFlow` — UI state exposed to Composables
- `debounce(500)` — search query debouncing in SearchViewModel
- `distinctUntilChanged()` — prevent duplicate API calls

---

## 📦 Dependencies Summary

```toml
# Core
androidx.core-ktx = "1.13.1"
kotlin = "2.0.21"

# Compose BOM
compose-bom = "2024.09.03"

# DI
hilt = "2.51.1"

# Database
room = "2.6.1"

# Network
retrofit = "2.11.0"
okhttp = "4.12.0"

# Navigation
navigation-compose = "2.8.2"

# Image
coil = "2.7.0"
```

---

## 🧪 Testing (suggested)

```
test/
├── data/repository/NewsRepositoryImplTest.kt   # Unit test with mock API + DAO
├── domain/usecase/GetTopHeadlinesUseCaseTest.kt
└── presentation/HomeViewModelTest.kt           # StateFlow tests
```

Run tests:
```bash
./gradlew test                  # Unit tests
./gradlew connectedAndroidTest  # Instrumentation tests
```

---

## 📸 App Screens

```
┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│  📰 NewsDaily    │  │  🔍 Search       │  │  🔖 Saved        │
│  ┌────────────┐  │  │  ┌────────────┐  │  │                  │
│  │[Hero Card] │  │  │  │ Search Bar │  │  │  • Article 1     │
│  └────────────┘  │  │  └────────────┘  │  │  • Article 2     │
│  Latest          │  │  results...      │  │  • Article 3     │
│  • Article 1     │  │                  │  │                  │
│  • Article 2     │  │                  │  │                  │
│  [Home|Search|💾]│  │  [Home|Search|💾]│  │  [Home|Search|💾]│
└──────────────────┘  └──────────────────┘  └──────────────────┘
```

---

## ⚠️ Notes

- **NewsAPI free tier** only allows fetching headlines for US (`country=us`). For production, upgrade to a paid plan.
- The API key is embedded in `BuildConfig` for simplicity. In production, use a backend proxy to hide the key.
- `fallbackToDestructiveMigration()` is used for Room — replace with proper migrations in production.
