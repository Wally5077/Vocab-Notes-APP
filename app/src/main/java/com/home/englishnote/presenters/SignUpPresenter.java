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
import com.home.englishnote.views.BaseView;
import com.home.englishnote.utils.HashUtil;
import com.home.englishnote.utils.ThreadExecutor;
import com.home.englishnote.utils.VerifyInputFormatUtil;


public class SignUpPresenter {

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
                verifyUserInfo(firstName, lastName, age,
                        email, password, passwordConfirmation);
                Member member = new Member(firstName, lastName,
                        Integer.valueOf(age), email, password);
                Credentials credentials = member.getCredentials();
                Token token = memberRepository.signUp(member, credentials);
                member.setId(token.getMemberId());
                member.setToken(token);
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
        VerifyInputFormatUtil.verifyInputEmpty(firstName, lastName, age, email, password);
        VerifyInputFormatUtil.verifyEmailFormat(email);
        VerifyInputFormatUtil.verifyPasswordFormat(password);
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
