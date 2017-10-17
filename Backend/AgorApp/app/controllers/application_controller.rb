class ApplicationController < ActionController::API

  #protect_from_forgery with: :exception , unless: -> { valid_active_token  }

  def valid_active_token
    p "Validating Token"
    atoken = request.query_parameters['active_token']

    if atoken
      p "API Key succesfully validated"
      return User.exists?(active_token: atoken)
    else
      p "API KEY not found"
      return false
    end
  end
end
