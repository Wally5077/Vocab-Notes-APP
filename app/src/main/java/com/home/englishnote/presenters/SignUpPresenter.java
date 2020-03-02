package com.home.englishnote.presenters;


import com.home.englishnote.exceptions.EmailDuplicatedException;
import com.home.englishnote.exceptions.EmailFormatInvalidException;
import com.home.englishnote.exceptions.PasswordConfirmationNotMatchException;
import com.home.englishnote.exceptions.PasswordFormatInvalidException;
import com.home.englishnote.exceptions.UserInputEmptyException;
import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.repositories.MemberRepository;
import com.home.englishnote.utils.BaseView;
import com.home.englishnote.utils.ThreadExecutor;


public class SignUpPresenter extends VerifyFormatPresenter {

    private SignUpView signUpView;
    private MemberRepository memberRepository;
    private ThreadExecutor threadExecutor;

    public SignUpPresenter(SignUpView signUpView,
                           MemberRepository memberRepository,
                           ThreadExecutor threadExecutor) {
        this.signUpView = signUpView;
        this.memberRepository = memberRepository;
        this.threadExecutor = threadExecutor;
    }

    public void signUp(String firstName, String lastName, String age,
                       String email, String password, String passwordConfirmation) {
        threadExecutor.execute(() -> {
            try {
//                verifyUserInfo(firstName, lastName, age,
//                        email, password, passwordConfirmation);
                // Todo There's a test code below this line
//                String defaultAge = (age.equals(VocabularyNoteKeyword.DEFAULT_SPINNER_WORD)) ?
//                        VocabularyNoteKeyword.DEFAULT_AGE : age;
                String hashPassword = hashPassword(password);
                Member member = new Member(firstName, lastName,
                        Integer.valueOf(age), email, hashPassword);
                Credentials credentials = member.getCredentials();
                Token token = memberRepository.signUp(member, credentials);
                threadExecutor.executeUiThread(
                        () -> signUpView.onSignUpSuccessfully(member, token));
            } catch (UserInputEmptyException err) {
                threadExecutor.executeUiThread(signUpView::onUserInputEmpty);
            } catch (EmailFormatInvalidException err) {
                threadExecutor.executeUiThread(signUpView::onEmailFormatInvalid);
            } catch (PasswordFormatInvalidException err) {
                threadExecutor.executeUiThread(signUpView::onPasswordFormatInvalid);
            } catch (PasswordConfirmationNotMatchException err) {
                threadExecutor.executeUiThread(signUpView::onPasswordConfirmationNotMatch);
            } catch (EmailDuplicatedException err) {
                threadExecutor.executeUiThread(signUpView::onEmailDuplicated);
            }
        });
    }

    private void verifyUserInfo(String firstName, String lastName, String age,
                                String email, String password, String passwordConfirmation) {
        // Todo
        verifyUserInfoEmpty(firstName, lastName, age, email, password);
        verifyEmailFormat(email);
        verifyPasswordFormat(password);
        verifyPasswordConfirmation(password, passwordConfirmation);
    }

    private void verifyPasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new PasswordConfirmationNotMatchException();
        }
    }

    public interface SignUpView extends BaseView {

        void onPasswordConfirmationNotMatch();

        void onEmailDuplicated();

        void onSignUpSuccessfully(Member member, Token token);
    }
}
