(ns hs.core)

(def init-world
  {:epoch 0
   :health-samura {:samurai  {}
                   :projects {}
                   :products {}
                   :laws {:KSt 100 ;; karma surv thr
                          :KIn 45 ;; karma in
                          }
                   :bad-imperators {}}})

(defn mk-samurai
  {:kps 0 ;; karma points
   :roles #{}})

(defn get-in-rule [world new-samurai]
  (when (> (get-in world [:health-samurai :laws :KIn])
           (:kps new-samurai))))

(defn survive-rule [world samurai]
  (> (:kps samurai)
     (get-in world [:health-samurai :laws :KSt])))

(defn survilance [world changes]
  (update-in world [:health-samurai :samurai]
             (fn [ss]
               (->> ss (remove #(survive-rule world %) ss)))))

(defn cycle [world changes]
  (-> world
      (distribute-income changes)
      (new-samurai changes)
      (survilance changes)))
