package ai.deepfine.utility.error

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 2/1/21
 * @version 1.0.0
 */

sealed interface Failure

object NetworkConnectionFailure : Failure
data class ServerFailure(val message: String? = null) : Failure