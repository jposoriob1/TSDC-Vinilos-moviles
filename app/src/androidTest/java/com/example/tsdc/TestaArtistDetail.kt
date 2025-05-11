
package com.example.tsdc

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestArtistDetailScreenApi {

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
        composeTestRule
            .onAllNodesWithTag("card_artista_Rubén Blades Bellido de Luna")
            .onFirst()
            .performClick()

        // Espera a que se cargue el detalle
        composeTestRule.waitUntil(timeoutMillis = 7000) {
            composeTestRule
                .onAllNodesWithTag("Información del Músico")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("detalle_nombre_musico").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Fecha_nacimiento").assertIsDisplayed()
        composeTestRule.onNodeWithTag("detalle_descripcion_musico").assertIsDisplayed()
    }

    @Test
    fun seMuestrBandas() {
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
        composeTestRule
            .onAllNodesWithTag("card_artista_Queen")
            .onFirst()
            .performClick()

        // Espera a que se cargue el detalle
        composeTestRule.waitUntil(timeoutMillis = 6000) {
            composeTestRule
                .onAllNodesWithTag("Información de la Banda")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("detalle_nombre_banda").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Fecha_creación").assertIsDisplayed()
        composeTestRule.onNodeWithTag("description_band").assertIsDisplayed()
    }

    @Test
    fun BotonVolverArtist() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
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