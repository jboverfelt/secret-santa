(ns secret-santa.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.session.memory :refer [memory-store]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [noir.session :as session]
            [noir.util.middleware :as noir]
            [noir.response :as resp]
            [compojure.route :as route]
            [secret-santa.models.user :as user]
            [secret-santa.routes.home :refer [home-routes]]
            [secret-santa.routes.auth :refer [auth-routes]]
            [secret-santa.routes.admin :refer [admin-routes]]
            [secret-santa.routes.user :refer [user-routes]]
            [secret-santa.routes.wishlist :refer [wishlist-routes]]))

(defn init []
  (println "secret-santa is starting"))

(defn destroy []
  (println "secret-santa is shutting down"))

(defn force-login [_]
  (session/flash-put! :warn "Please log in or sign up")
  (resp/redirect "/login"))

(defn force-not-found [_]
  (resp/status 404 (resp/content-type "text/html" "Not Found")))

(defn user-access [_]
  (session/get :user))

(defn admin-access [_]
  (when-let [user-id (session/get :user)]
    (user/admin? user-id)))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> [auth-routes home-routes admin-routes user-routes wishlist-routes app-routes]
      (noir/app-handler :access-rules [{:on-fail force-login :rule user-access}
                                       {:on-fail force-not-found :rule admin-access :uris ["/admin" "/admin/*"]}]
                        :session-options {:store (memory-store)}
                        :middleware [wrap-anti-forgery noir/wrap-strip-trailing-slash])))
