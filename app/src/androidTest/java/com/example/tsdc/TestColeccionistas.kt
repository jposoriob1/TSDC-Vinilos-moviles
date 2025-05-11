package com.example.tsdc

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestColeccionistasScreenApi {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegaYSeMuestraListaColeccionistas() {
        // Paso 1: Simula clic para abrir la pantalla de coleccionistas
        composeTestRule
            .onNodeWithText("COLECCIONISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("lista_coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que la lista esté visible
        composeTestRule
            .onNodeWithTag("lista_coleccionistas")
            .assertIsDisplayed()
    }

    @Test
    fun verificarElementosEnListaColeccionistas() {
        // Paso 1: Simula clic para abrir la pantalla de coleccionistas
        composeTestRule
            .onNodeWithText("COLECCIONISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("lista_coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que al menos un coleccionista esté visible
        // Esperamos hasta que aparezca algún elemento con email (todos los coleccionistas tienen email)
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("@", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun botonVolverFunciona() {
        // Paso 1: Simula clic para abrir la pantalla de coleccionistas
        composeTestRule
            .onNodeWithText("COLECCIONISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("lista_coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Click en la flecha de volver
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        // Paso 4: Verificar que volvimos a la pantalla principal
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("TSDC").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("TSDC").assertIsDisplayed()
        composeTestRule.onNodeWithText("VINYLS").assertIsDisplayed()
    }

    @Test
    fun verificarDetallesColeccionista() {
        // Paso 1: Simula clic para abrir la pantalla de coleccionistas
        composeTestRule
            .onNodeWithText("COLECCIONISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("lista_coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Espera a que aparezca algún coleccionista con email
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithText("@", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 4: Verifica que la lista de coleccionistas esté visible y contenga elementos
        composeTestRule
            .onNodeWithTag("lista_coleccionistas")
            .assertIsDisplayed()

        // Paso 5: Verifica que hay al menos un nodo con email en la lista
        val emailNodes = composeTestRule
            .onAllNodesWithText("@", substring = true)
            .fetchSemanticsNodes()

        assert(emailNodes.isNotEmpty()) { "No se encontraron nodos con email (@) en la lista de coleccionistas" }
    }

    @Test
    fun verificarTituloColeccionistas() {
        // Paso 1: Simula clic para abrir la pantalla de coleccionistas
        composeTestRule
            .onNodeWithText("COLECCIONISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule
                .onAllNodesWithTag("lista_coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que el título "Coleccionistas" esté visible
        composeTestRule.onNodeWithText("Coleccionistas").assertIsDisplayed()
    }
}
