package pjavatest;

import org.junit.Test;
import pjava.Categories;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unused")
public class CategoriesTest {
	@Test
	public void ajouterEtSupprimerTest() {
		Categories categories = new Categories();

		//On vérifie que les deux catégories par défaut sont bien présentes
		assertEquals(2,categories.getLcategorie().size());

		String categorie = "Nouvelle categorie";
		String categorieModifiee = "Categorie modifiee";

		//vérifier qu'une catégorie est bien ajoutée
		categories.ajouterCat(categorie);
		assertEquals(3,categories.getLcategorie().size());


		//vérifier qu'une catégorie est bien modifiée
		categories.modifierCat(categorie,categorieModifiee);
		assertEquals("Categorie modifiee",categories.getLcategorie().get(2));

		//vérifier qu'une catégorie est bien supprimée
        String str = categories.supprimerCat(categorieModifiee);
		assertEquals(2,categories.getLcategorie().size());

	}
}
