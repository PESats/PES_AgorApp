class UsersController < ApplicationController

  before_action :set_user, only: [:show, :edit, :update, :destroy]

  def index
  end

  def new
    @user = User.new
  end

  def create
    @user = User.new(user_params)
    respond_to do |format|
      if @user.save
        format.json { render json: @user.as_json(root: false, only: [:active_token]) , status: :created }
      else
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  def show
  end

  def edit
  end

  def update
  end

  def destroy
    @user.destroy
    respond_to do |format|
      format.json { head :no_content }
    end
  end
  
  
  # TODO:
  def login
  end
  
  def logout
  end

  private
################################################################################

  def user_params
    params.permit(:id, :name, :image_url, :platform_name)
  end

  #TODO escollir un dels metodes per la instancia d'usuari actual

  def set_user
      @user = User.find(params[:id])
  end

end
