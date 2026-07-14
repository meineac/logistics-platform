# Logistics Platform

A command-line logistics management system for assembling, filtering, sorting, and exporting shipment data. Built as a showcase of **OOP design patterns** — the project uses State, Decorator, Template Method, and Strategy patterns to create a flexible, extensible data processing pipeline.

## Architecture

```
├── models/
│   ├── cargo/              # Shippable, CargoItem, SingleCargoBatch, MixedCargoBatch
│   ├── delivery/           # Shipment, PayloadBuilder
│   └── transport/          # Transport hierarchy (Air, Land, Water) + TransportType enum
│
├── logistics/
│   ├── management/         # ShipmentAssembler, filtering (Strategy), sorting
│   └── transportation/     # TransportProvider implementations (Air, Land, Water)
│
├── service/                # LogisticsService, DataExportService
│
├── ui/state/               # State Machine — menu navigation
│   ├── MenuState.java              # State interface
│   ├── MenuContext.java            # Context holder
│   ├── MainMenuState.java          # Initial state
│   ├── DataLoadedMenuState.java    # After data import
│   ├── ShipmentBuilderState.java   # Shipment assembly
│   ├── ShipmentResultsState.java   # Results display
│   └── ExportResultsState.java     # Export selection
│
├── utils/
│   ├── loader/             # Template Method — data import (CSV, JSON, XML)
│   ├── export/
│   │   ├── format/         # Strategy — export format (CSV, JSON, XML)
│   │   └── decorators/     # Decorator — compression, encryption
│   └── helper/             # EntityAssembler
│
└── App.java                # Entry point
```

### Design Patterns Used

| Pattern | Where | Purpose |
|---------|-------|---------|
| **State** | `ui/state/` | Menu-driven navigation — each screen is a state with its own input handling and transitions |
| **Decorator** | `utils/export/decorators/` | Wraps data exporters with optional compression and encryption without modifying the base exporter |
| **Template Method** | `utils/loader/DataLoaderTemplate` | Defines the skeleton for loading data from different formats; subclasses implement format-specific parsing |
| **Strategy** | `logistics/management/filtering/` + `utils/export/format/` | Interchangeable filtering criteria (max cost, max time) and export formats (CSV, JSON, XML) |

## Features

- **Multi-Format Import** — load logistics data from CSV, JSON, or XML
- **Shipment Assembly** — combine cargo items with transport providers
- **Configurable Filtering** — filter shipments by cost or delivery time thresholds
- **Sorting** — sort assembled shipments by various criteria
- **Multi-Format Export** — export results to CSV, JSON, or XML
- **Export Decorators** — optionally compress and/or encrypt exported data (chainable)
- **Interactive CLI** — state-machine-driven menu system

## Getting Started

### Prerequisites

- Java 25+
- Maven 3.9+

### Run

```bash
git clone https://github.com/meineac/logistics-platform.git
cd logistics-platform
mvn compile exec:java -Dexec.mainClass="App"
```

Sample data files are included in `src/main/resources/imports/`.

## Example Workflow

1. **Load data** — select import format and load logistics data
2. **Assemble shipments** — combine cargo with available transport
3. **Filter results** — apply cost or time constraints
4. **Export** — choose format and optional compression/encryption
