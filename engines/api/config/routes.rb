Api::Engine.routes.draw do
  post "login", to: "Sessions#login"
  resources :words

  match 'words/from_pl/:in_polish', to: 'Words#get_english_translations'
  match 'words/from_en/:in_english', to: 'Words#get_polish_translations'
end
