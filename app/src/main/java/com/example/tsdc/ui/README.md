# UI Package

The `ui` package contains all components related to the user interface of the application. This includes views and viewmodels.

## Sub-packages

### `view`
- Contains UI components such as Activities, Fragments, or Composables.
- Responsible for rendering the data provided by the ViewModel and handling user interactions.

### `viewmodel`
- Contains ViewModel classes that manage UI-related data and business logic.
- ViewModels interact with repositories to fetch data and expose it to the views using LiveData or StateFlow.

## Example
- `view/UserScreen.kt`: A composable function that displays a list of users.
- `viewmodel/UserViewModel.kt`: Manages user data and business logic for the `UserScreen`.