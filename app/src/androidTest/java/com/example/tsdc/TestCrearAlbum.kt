package com.example.tsdc

import androidx.compose.ui.test.performTextInput


import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick

import org.junit.Rule
import org.junit.Test

class TestCrearAlbumScreen {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegaYSeMuestraListaAlbumes() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
        composeTestRule
            .onNodeWithText("ALBUMES")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag("lista_albumes")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que la lista esté visible
        composeTestRule
            .onNodeWithTag("lista_albumes")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("CREAR ÁLBUM +")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithTag("album_nombre").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("album_nombre").performTextInput("Mi Álbum Test")
        composeTestRule.onNodeWithTag("album_portada").performTextInput("https://img.url/portada.jpg")
        composeTestRule.onNodeWithTag("album_descripcion").performTextInput("Este es un álbum de prueba")

        composeTestRule.onNodeWithTag("album_fecha").performClick()
        composeTestRule.onNodeWithTag("album_fecha").performTextInput("1984-08-01")

        // Simula selección de género y sello (abre menú y selecciona)
        composeTestRule.onNodeWithTag("album_genero").performClick()
        composeTestRule.onNodeWithText("Salsa").performClick() // ajusta según el enum .value

        composeTestRule.onNodeWithTag("album_sello").performClick()
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithText("ELEKTRA").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("album_boton_crear").performClick()


    }

    @Test
    fun botonCancelarCrearALbum() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
        composeTestRule
            .onNodeWithText("ALBUMES")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag("lista_albumes")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que la lista esté visible
        composeTestRule
            .onNodeWithTag("lista_albumes")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("CREAR ÁLBUM +")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithTag("album_nombre").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("btnCancelar").performClick()

    }
}