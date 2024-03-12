package strings

/**
 * Implementation of the AppStrings for the French translation.
 */
data object FrStrings: AppStrings {
    override val openFile: String = "Ouvrir un fichier"
    override val saveFile: String = "Enregistrer le fichier"
    override val saveAs: String = "Enregistrer sous..."
    override val fileName: String = "Nom du fichier"
    override val validate: String = "Valider"
    override val cancel: String = "Annuler"

    override val newFile: String = "Nouveau fichier"
    override val fileSaved: String = "Fichier sauvegardé"
    override val fileCouldNotBeSaved: String = "Le fichier n'a pas pu être sauvegardé"

    override fun saveFileNameIn(filename: String): String = "Enregistrer $filename dans..."
}