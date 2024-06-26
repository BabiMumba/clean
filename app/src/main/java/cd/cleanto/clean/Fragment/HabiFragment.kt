package cd.cleanto.clean.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import cd.cleanto.clean.Adapters.CartHabiAda
import cd.cleanto.clean.Models.cart_item
import cd.cleanto.clean.R
import cd.cleanto.clean.Utils.Utils
import cd.cleanto.clean.View.CommandeActivity
import cd.cleanto.clean.View.MyMapsActivity
import cd.cleanto.clean.databinding.FragmentHabiBinding


class HabiFragment : AppCompatActivity() {
    var item_count = 0
    lateinit var binding : FragmentHabiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHabiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        binding.back.setOnClickListener {
            onBackPressed()
        }


    }

    fun init() {
        //initialisation des elements de la page
        val liste_habi = ArrayList<cart_item>()
        liste_habi.add(cart_item(1, "T-shirt", 5.0,image = "https://cdn-icons-png.flaticon.com/128/4715/4715310.png"))
        liste_habi.add(cart_item(2, "short", 7.0,image ="https://cdn-icons-png.flaticon.com/128/7104/7104604.png"))
        liste_habi.add(cart_item(3, "jeans", 34.0,image = "https://cdn-icons-png.flaticon.com/128/599/599580.png"))
        liste_habi.add(cart_item(5, "pull", 6.0,image = "https://cdn-icons-png.flaticon.com/128/9228/9228255.png"))
        liste_habi.add(cart_item(6, "Veste", 60.0,image = "https://cdn-icons-png.flaticon.com/128/2806/2806153.png"))
        liste_habi.add(cart_item(7, "Manteau", 24.0,image = "https://cdn-icons-png.flaticon.com/128/3893/3893171.png"))
        liste_habi.add(cart_item(8, "Chemise", 14.0,image = "https://cdn-icons-png.flaticon.com/128/4722/4722182.png"))
        liste_habi.add(cart_item(9, "Costume", 58.0,image = "https://cdn-icons-png.flaticon.com/128/703/703272.png"))
        liste_habi.add(cart_item(10, "jupe", 18.0,image = "https://cdn-icons-png.flaticon.com/128/4507/4507761.png"))
        liste_habi.add(cart_item(11, "Robe simple", 23.0,image = "https://cdn-icons-png.flaticon.com/128/363/363834.png"))

        //creation de l'adapter
        binding.recyclerHab.apply {
            adapter = CartHabiAda(liste_habi)
        }
        //ajout de l'evenement de click sur le bouton commander
        val liste_panier = ArrayList<cart_item>()
        (binding.recyclerHab.adapter as CartHabiAda).setOnItemClickListener(object :
            CartHabiAda.OnItemClickListener {
            override fun onItemClick(item: cart_item) {
                update_nb(item.price)
                item_count++
                binding.commandeLyt.commanderBtn.text = "Commander ($item_count)"
                binding.cart.cartBadge.text = item_count.toString()
                if (liste_panier.contains(item)) {
                    val index = liste_panier.indexOf(item)
                    val new_item = liste_panier[index]
                    new_item.quantity += 1
                    liste_panier[index] = new_item
                } else {
                    liste_panier.add(item)
                }
            }
        })

        binding.cart.cart.setOnClickListener {
            //prendre la liste des articles ajoutés
            if (item_count > 0) {
                passdata(liste_panier)
            } else {
                Utils.showtoast(this, "Veuillez ajouter des articles à votre panier")
            }

        }
        binding.commandeLyt.commanderBtn.setOnClickListener {
            if (item_count > 0) {
                passdata(liste_panier)
            } else {
                Utils.showtoast(this, "Veuillez ajouter des articles à votre panier")
            }
        }
    }

    fun passdata(liste_panier: ArrayList<cart_item>) {
        val intent = Intent(this, CommandeActivity::class.java)
        intent.putParcelableArrayListExtra("liste_panier", liste_panier)
        startActivity(intent)
    }

    fun update_nb(prix: Double) {
        // Get the current total from the TextView
        var nb = binding.commandeLyt.totalPrice.text.toString().toInt()
        // Increment the total
        nb += prix.toInt()
        // Update the TextView with the new total
        binding.commandeLyt.totalPrice.text = "$nb"
    }

}