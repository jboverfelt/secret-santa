(defproject secret-santa "0.1.0-SNAPSHOT"
  :description "Secret Santa for distributed friend groups"
  :url "https://github.com/jboverfelt/secret-santa"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.logic "0.8.7"]
                 [compojure "1.1.6"]
                 [ring-anti-forgery "0.3.0"]
                 [lib-noir "0.8.1"]
                 [clj-time "0.6.0"]
                 [clojurewerkz/mailer "1.0.0"]
                 [com.novemberain/monger "1.7.0"]
                 [autoclave "0.1.6"]
                 [markdown-clj "0.9.43"]
                 [de.ubercode.clostache/clostache "1.3.1"]
                 [ring-server "0.3.0"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler secret-santa.handler/app
         :init secret-santa.handler/init
         :destroy secret-santa.handler/destroy}
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [javax.servlet/servlet-api "2.5"]]}})
