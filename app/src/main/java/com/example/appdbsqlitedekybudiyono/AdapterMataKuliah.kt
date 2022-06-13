package com.example.appdbsqlitedekybudiyono


import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterMataKuliah (
    private val getContext: Context,
    private val CustomListItem:ArrayList<MataKuliah>
):ArrayAdapter<MataKuliah>(getContext, 0 , CustomListItem){
    override  fun getView (position:Int,convertView: View?,parent: ViewGroup):View{
        var listLayout = convertView
        val holder : ViewHolder
        if (listLayout == null){
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.activity_layout_item,parent,false)
            holder = ViewHolder()
            with(holder){
                tvNmMatkul = listLayout.findViewById(R.id.tvNmMatkul)
                tvKdMatkul = listLayout.findViewById(R.id.tvkdMatkul)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus= listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = CustomListItem[position]
        holder.tvNmMatkul!!.setText(listItem.NmMatkul)
        holder.tvKdMatkul!!.setText(listItem.kdMatkul)



        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, PengentriData::class.java)
            i.putExtra("kode", listItem.kdMatkul)
            i.putExtra("nama", listItem.NmMatkul)
            i.putExtra("sks", listItem.sks)
            i.putExtra("sifat", listItem.sifat)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdMatkul!!.text
            val nama = holder .tvNmMatkul!!.text
            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        apakah anda yakin menghapus data ini?
                        
                        $nama [$kode]
                        """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Mata Kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }
        return listLayout!!
    }
    class ViewHolder {
        internal var tvNmMatkul: TextView? = null
        internal var tvKdMatkul: TextView? = null
        internal var btnEdit : ImageButton? = null
        internal var btnHapus : ImageButton? = null

    }
}