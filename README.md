## Arcana Flux

<img width="640" height="270" alt="tarot-app-screenshot" src="screenshot-rc-tarot.png" />

Tarot card reading app built at [The Recurse Center](https://recurse.com) using Android [Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation) as a learning project.

The app allows users to draw tarot cards and receive readings with upright and reversed meanings. Users can choose from different card spreads (1 card or 3-cards), save their readings to a history, and review past readings.

## Running the App

Clone the repo:
```bash
git clone https://github.com/nadia-nh/rc-android-tarot.git
cd rc-android-tarot
```


## How the App Works

The app follows the MVVM architecture pattern with Jetpack Compose for UI and Room for data persistence.

- **TarotMain**  
  Main composable that manages screen navigation (Menu, Result, History) and handles the back button.

- **TarotViewModel**  
  Manages UI state including the current spread, drawn cards, and reading history. Coordinates card drawing and database operations.

- **TarotDeck**  
  Contains the complete 78-card tarot deck with meanings for both upright and reversed positions.

- **Screens**  
  - `MenuScreen` – Select card spread and start a reading
  - `ResultsScreen` – Display drawn cards with meanings, tap to reveal
  - `HistoryScreen` – View past readings saved to the database

- **Database (Room)**  
  - `TarotDatabase` – SQLite database for storing readings
  - `TarotDao` – Data access object with queries for saving and retrieving readings
  - `TarotRepository` – Single source of truth for data operations

- **Theme**  
  Material 3 theming with custom colors, typography, spacing, and shapes defined in the `ui/theme` package.

## Resources

- [Jetpack Compose Documentation](https://developer.android.com/develop/ui/compose/documentation)  
  Official documentation for the declarative UI framework.

- [Room Database Documentation](https://developer.android.com/jetpack/androidx/releases/room)  
  Official documentation for the SQLite database abstraction.

- [Material 3 Design](https://m3.material.io/)  
  System used for the app's UI components.

- [Tarot API](https://tarotapi.dev)  
  REST API used for card data including names and meanings.

### Assets

- **Card images**  
  Creator: [Dettamada](https://dettamada.itch.io/)  
  Source: [Tarot Playing Cards](https://dettamada.itch.io/tarot-playing-cards)  
  License: CC0

---

Made with <3 at [The Recurse Center](https://recurse.com).  
