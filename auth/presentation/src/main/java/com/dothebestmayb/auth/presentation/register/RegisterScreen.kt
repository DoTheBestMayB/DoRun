package com.dothebestmayb.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dothebestmayb.auth.domain.PasswordValidationState
import com.dothebestmayb.auth.domain.UserDataValidator
import com.dothebestmayb.auth.presentation.R
import com.dothebestmayb.core.presentation.designsystem.CheckIcon
import com.dothebestmayb.core.presentation.designsystem.CrossIcon
import com.dothebestmayb.core.presentation.designsystem.DoRunTheme
import com.dothebestmayb.core.presentation.designsystem.DorunDarkGray
import com.dothebestmayb.core.presentation.designsystem.DorunDarkRed
import com.dothebestmayb.core.presentation.designsystem.DorunGray
import com.dothebestmayb.core.presentation.designsystem.DorunGreen
import com.dothebestmayb.core.presentation.designsystem.EmailIcon
import com.dothebestmayb.core.presentation.designsystem.Poppins
import com.dothebestmayb.core.presentation.designsystem.components.DoRunActionButton
import com.dothebestmayb.core.presentation.designsystem.components.DoRunPasswordTextField
import com.dothebestmayb.core.presentation.designsystem.components.DoRunTextField
import com.dothebestmayb.core.presentation.designsystem.components.GradientBackground
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium,
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = DorunGray,
                    )
                ) {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    // ClickableText를 사용할 때는 클릭 영역을 지정하기 위해 필요했으나
                    // ClickableText가 deprecated 되고, withLink를 사용하면 되므로 불필요함
//                    pushStringAnnotation(
//                        tag = "clickable_text",
//                        annotation = stringResource(id = R.string.login)
//                    )
                    // now in android 예제
                    // https://github.com/android/nowinandroid/blob/d15c739812f25401b21614fe0f7e18534d285921/feature/search/src/main/kotlin/com/google/samples/apps/nowinandroid/feature/search/SearchScreen.kt#L231
                    withLink(
                        link = LinkAnnotation.Clickable(
                            tag = "clickable_text",
                            linkInteractionListener = {
                                onAction(RegisterAction.OnLoginClick)
                            }
                        )
                    ) {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = Poppins
                            )
                        ) {
                            append(stringResource(id = R.string.login))
                        }
                    }
                }
            }
            Text(
                text = annotatedString,
            )
            // deprecated
//            ClickableText(
//                text = annotatedString,
//                onClick = { offset ->
//                    annotatedString.getStringAnnotations(
//                        tag = "clickable_text",
//                        start = offset,
//                        end = offset
//                    ).firstOrNull()?.let {
//                        onAction(RegisterAction.OnLoginClick)
//                    }
//                }
//            )
            Spacer(modifier = Modifier.height(48.dp))
            DoRunTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else {
                    null
                },
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email,
            )
            Spacer(modifier = Modifier.height(16.dp))
            DoRunPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityCheck)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength,
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumber,
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_lowercase_character,
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter,
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    id = R.string.contains_uppercase_character,
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter,
            )
            Spacer(modifier = Modifier.height(32.dp))
            DoRunActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if (isValid) {
                DorunGreen
            } else {
                DorunDarkRed
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
private fun RegisterScreenPreview() {
    DoRunTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true,
                ),
            ),
            onAction = {},
        )
    }
}