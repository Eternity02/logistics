<template>
  <div>
    <a-spin size="large" :spinning="spinning">
      <a-row :gutter="20">
        <a-col :span="8" class="item">
          <a-card hoverable class="add-item" @click="visible = true">
            <a-icon type="plus"/>
            添加仓库
          </a-card>
        </a-col>
        <a-col :span="8" v-for="(item, index) in this.data" :key="index" class="item">
          <a-card hoverable>
            <template slot="actions" class="ant-card-actions">
              <span>负责人: {{ item.principle }}</span>
              <span>
                <router-link :to="'/warehouse/' + item.id">
                  <a-icon type="bar-chart"/> 库存管理
                </router-link>
              </span>
            </template>
            <a-card-meta
                :title="(index+1) +'号仓库'"
                :description="'ID: ' + item.id">
              <img class="image" slot="avatar" :src="imgList[getRandomImg]"
                   alt=""/>
            </a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </a-spin>

    <a-modal
        title="新增仓库"
        :visible="visible"
        @ok="submit"
        @cancel="visible = false"
    >
      <a-form-model layout="inline" >
        <a-form-model-item label="仓库负责人">
          <a-select v-model="form.principle" style="width: 200px" >
            <a-select-option :value="employee.name" v-for="(employee,index) in employees" :key="index">
              {{employee.name}}
            </a-select-option>

          </a-select>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {FindAllWarehouse, SaveWarehouse} from "@/api/warehouse";
import {FindAllEmployee} from "@/api/employee";

export default {
  name: "WareHouse",
  data() {
    return {
      visible: false,
      form: {id: '', principle: ''},
      spinning: false,
      data: [],
      imgList: [
        require('../../assets/warehouse0.svg'),
        require('../../assets/warehouse1.svg'),
        require('../../assets/warehouse2.svg'),],
      employees: []
    }
  },

  mounted() {
    this.loadData()
  },
  computed: {
      getRandomImg: function (index) {
        return Math.floor(Math.random() * 3)
      }
  },
  methods: {

    loadData() {
      this.spinning = true
      FindAllWarehouse().then((res) => {
        if (res.status) this.data = res.data
        setTimeout(() => {
          this.spinning = false
        }, 600)
      })
      FindAllEmployee().then((res)=>{
        if(res.status){
          this.employees = [...res.data]
          this.form.principle = this.employees[0].name
        }
      })
    },

    submit() {
      SaveWarehouse(this.form).then((res) => {
        if (res.status) this.$message.success("添加成功")
        this.visible = false
        this.loadData()
      })
    }

  },

}
</script>

<style scoped>
.add-item {
  text-align: center;
  line-height: 120px;
  min-height: 120px;
  border: 1px dashed #91949c;
}

.add-item:hover {
  border: 1px dashed #5a84fd;
  color: #5a84fd;
}

.item {
  margin-bottom: 20px;
}

.image {
  width: 80px;
  height: 80px;
}
</style>
