package strings

/**
 * Implementation of the AppStrings for the French translation.
 */
data object FrStrings: AppStrings {
    override val file: String = "Fichier"

    override val openFile: String = "Ouvrir un fichier"
    override val noFileOpen: String = "Aucun ficher ouvert"
    override val createFile: String = "Créer un fichier"

    override val saveFile: String = "Enregistrer"
    override val saveAs: String = "Enregistrer sous..."
    override val exportAsPdf: String = "Exporter en PDF"

    override val fileName: String = "Nom du fichier"
    override val validate: String = "Valider"
    override val cancel: String = "Annuler"

    override val newFilename: String = "Nouveau fichier"
    override val fileSaved: String = "Fichier sauvegardé"
    override val fileCouldNotBeSaved: String = "Le fichier n'a pas pu être sauvegardé"

    override fun saveFileNameIn(filename: String): String = "Enregistrer $filename dans..."

    override fun loadingImageAtPath(imagePath: String): String = "Chargement d'une image provenant du chemin suivant : $imagePath"
    override fun couldNotLoadImageAtPath(imagePath: String): String = "Impossible de charger l'image provenant du chemin suivant : $imagePath"
}