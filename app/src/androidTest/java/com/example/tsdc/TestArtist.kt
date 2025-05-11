package com.example.tsdc


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestArtistScreenApi {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun seMuestraArtistas() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
        composeTestRule
            .onNodeWithText("ARTISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 6000) {
            composeTestRule
                .onAllNodesWithTag("lista_artistas")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("MÚSICOS").performClick()

        // Verifica que se vean algunos datos del artista
        composeTestRule.waitUntil(timeoutMillis = 7000) {
            composeTestRule.onAllNodesWithText("Rubén Blades Bellido de Luna").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun seMuestraBandas() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
        composeTestRule
            .onNodeWithText("ARTISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 6000) {
            composeTestRule
                .onAllNodesWithTag("lista_artistas")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("BANDAS").performClick()

        // Verifica que se vean algunos datos de la banda
        composeTestRule.waitUntil(timeoutMillis = 7000) {
            composeTestRule.onAllNodesWithText("Queen").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun BotonVolverPrincipal() {
        // Paso 1: Simula clic para abrir la pantalla de artistas
        composeTestRule
            .onNodeWithText("ARTISTAS")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag("lista_artistas")
                .fetchSemanticsNodes().isNotEmpty()
        }

        //Click en la flecha de volver
        composeTestRule.onNodeWithContentDescription("Volver").performClick()
    }
}