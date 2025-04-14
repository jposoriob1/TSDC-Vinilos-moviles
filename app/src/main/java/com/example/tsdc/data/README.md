# Data Package

The `data` package contains all classes and components related to data handling in the application. This includes models, repositories, and services.

## Sub-packages

### `model`
- Contains data classes that represent the structure of the application's data.
- These classes are typically used to map data from APIs, databases, or other sources.

### `repository`
- Contains repository classes that act as a single source of truth for data.
- Repositories handle data fetching from local databases, remote APIs, or other sources and provide it to the ViewModel.

### `service`
- Contains service interfaces or classes for interacting with external APIs or services.
- These are typically implemented using libraries like Retrofit for network communication.

## Example
- `model/User.kt`: Represents a user entity.
- `repository/UserRepository.kt`: Handles fetching user data.
- `service/UserService.kt`: Defines API endpoints for user-related operations.