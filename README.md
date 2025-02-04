# Media Organizer - JavaFX Application
Media Organizer is a desktop application built with Java and JavaFX that allows users to organize movies into custom folders and pages. Users can easily create, manage, and view their media collections, complete with detailed metadata like runtime, ratings, and personal notes.

![Screenshot from 2025-02-![Screenshot from 2025-02-05 01-44-13](https://github.com/user-attachments/assets/fba122d1-1bb0-49ee-bad0-c9b1598646c4)
05 01-39-51](https://github.com/user-attachments/assets/f81078e9-db7f-4e30-ba77-f4459e28e160)
![Screenshot from 2025-02-05 01-43-33](https://github.com/user-attachments/assets/5d14e01b-dc96-4a8a-be7d-adc5f0533e24)


## Features
- Folder and Page Structure:

  - Organize media into customizable folders (e.g., "Watched Movies", "Games").
  - Create pages within folders to represent individual items with detailed metadata.

- Context Menus for Easy Management:

  - Right-click context menus for adding, editing, or removing folders and pages.
  - Flexible interaction with TreeView for easy folder and media navigation.

- Custom Metadata:

  - Store media details such as title, runtime, rating, poster image, and personal notes.

- Search:

  - Integrated IMDb search functionality to retrieve media metadata (e.g., movie title, rating, and poster).

- Persistence Between Sessions:

  - Automatically saves the structure of folders and media (TreeView structure) using JSON and Java serialization.
  - All media data is stored locally and reloaded when the application is reopened.

- Customizable Appearance:

  - Apply custom stylesheets (CSS) to personalize the UI.
  - Upload media poster images to visually represent media items.

## Technology Stack
- Java 17: Core programming language for building the application.
- JavaFX: UI framework for creating interactive and modern user interfaces.
- CSS: Used for styling interfaces in SceneBuilder and JavaFX.
- SceneBuilder: Visual layout tool for design JavaFX interfaces.
- Maven: Dependency management and project build automation.
- Gson: Library used for JSON serialization and deserialization.
- Java Serialization: Saves complex objects like TreeItems and metadata between application sessions.

## How to Run the Application
### 1. Clone the Repository:

```
git clone https://github.com/oguzhankokulu/media-organizer.git
cd media-organizer
```

### 2. Build the Project with Maven:

```
mvn clean install
```

### 3. Run the Application:

```
mvn javafx:run
```
