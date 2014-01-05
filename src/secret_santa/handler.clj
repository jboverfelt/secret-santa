(ns secret-santa.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.session.memory :refer [memory-store]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [noir.session :as session]
            [noir.util.middleware :as noir]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [secret-santa.routes.home :refer [home-routes]]
            [secret-santa.routes.wishlist :refer [wishlist-routes]]))

(defn init []
  (println "secret-santa is starting"))

(defn destroy []
  (println "secret-santa is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes wishlist-routes app-routes)
      (handler/site)
      (session/wrap-noir-session {:store (memory-store)})
      (noir/wrap-strip-trailing-slash)
      (wrap-base-url)))
