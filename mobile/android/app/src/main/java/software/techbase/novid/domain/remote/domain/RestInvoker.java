package software.techbase.novid.domain.remote.domain;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.remote.exception.AuthenticationException;
import software.techbase.novid.domain.remote.exception.BadRequestException;
import software.techbase.novid.domain.remote.exception.InternalServerException;
import software.techbase.novid.domain.remote.exception.NoContentException;
import software.techbase.novid.domain.remote.exception.ResourceNotFoundException;
import software.techbase.novid.domain.remote.exception.UnProcessableException;
import software.techbase.novid.domain.remote.exception.UnauthorizedException;
import software.techbase.novid.domain.remote.exception.UnknownException;


public abstract class RestInvoker<SERVICE, REQUEST, RESPONSE> {

    private SERVICE service;

    public RestInvoker(SERVICE service) {

        this.service = service;
    }

    protected abstract Call<RESPONSE> call(REQUEST request);

    public Observable<Response<RESPONSE>> invoke(final REQUEST request) {

        return Observable.create((ObservableOnSubscribe<Response<RESPONSE>>) emitter -> {

            try {
                Call<RESPONSE> call = RestInvoker.this.call(request);
                Response<RESPONSE> response = call.execute();

                if (!response.isSuccessful()) {

                    assert response.errorBody() != null;
                    String errorBody = response.errorBody().string();
                    XLogger.debug(this.getClass(), "Error Response : " + errorBody);
                    String errorCode = new JSONObject(errorBody).getString("errorCode");

                    switch (response.code()) {

                        case 204:
                            throw new NoContentException();

                        case 400:
                            throw new BadRequestException(errorCode);

                        case 401:
                            throw new UnauthorizedException(errorCode);

                        case 403:
                            throw new AuthenticationException(errorCode);

                        case 404:
                            throw new ResourceNotFoundException(errorCode);

                        case 422:
                            throw new UnProcessableException(errorCode);

                        case 500:
                            throw new InternalServerException();

                        default:
                            throw new UnknownException(errorCode);
                    }
                }

                emitter.onNext(response);
                emitter.onComplete();

            } catch (Exception e) {

                XLogger.debug(this.getClass(), e.toString());
                emitter.onError(e);
            }

        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    protected SERVICE getService() {

        return this.service;
    }
}