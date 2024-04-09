<script setup>
import authV1BottomShape from '@images/svg/bottom-illustration.svg?raw'
import authV1TopShape from '@images/svg/top-illustration.svg?raw'
import { VNodeRenderer } from '@layouts/components/VNodeRenderer'
import { themeConfig } from '@themeConfig'
import { reactive } from 'vue'
import axios from "@axios";
import { requiredValidator, emailValidator, lengthValidator } from '@validators';
import { useAppAbility } from '@/plugins/casl/useAppAbility';

const form = reactive({
    username: '',
    email: '',
    password: '',
})

const isPasswordVisible = ref(false)
const isLoading = ref(false);
const refForm = ref();
const ability = useAppAbility()

const register = async () => {
    const registerCustomerURL = "users/createCustomer";

    isLoading.value = true;
    const valid = await refForm.value.validate()
    if (valid.valid == false) {
        isLoading.value = false;
        return;
    }

    let data = {
        userName: form.username,
        password: form.password,
        email: form.email,
    }

    console.log("Regisetering user with data: ", data);

    axios.post(registerCustomerURL, data)
        .then(response => {
            console.log("Response: ", response.data);
            localStorage.setItem("UserType", response.data.userType);
            localStorage.setItem("UserId", response.data.userId);
            localStorage.setItem("UserName", response.data.userName);

            const userAbilities = [
                {
                    action: "manage",
                    subject: response.data.userType
                }
            ];

            localStorage.setItem('userAbilities', JSON.stringify(userAbilities));
            ability.update(userAbilities);

        })
        .catch(error => {
            console.log(error.message);
        });
    isLoading.value = false;
}

</script>

<template>
    <div class="auth-wrapper d-flex align-center justify-center pa-4">
        <div class="position-relative">
            <!-- 👉 Top shape -->
            <VNodeRenderer :nodes="h('div', { innerHTML: authV1TopShape })"
                class="text-primary auth-v1-top-shape d-none d-sm-block" />

            <!-- 👉 Bottom shape -->
            <VNodeRenderer :nodes="h('div', { innerHTML: authV1BottomShape })"
                class="auth-v1-bottom-shape d-none d-sm-block text-primary" />

            <VCard class="auth-card px-2" max-width="448">
                <VCardItem class="justify-center">
                    <template #prepend>
                        <div class="d-flex">
                            <VNodeRenderer :nodes="themeConfig.app.logo" />
                        </div>
                    </template>

                    <VCardTitle class="auth-title">
                        {{ themeConfig.app.title }}
                    </VCardTitle>
                </VCardItem>

                <VCardText>
                    <h6 class="text-h5 text-center mb-1">
                        Customer Registration
                    </h6>
                </VCardText>

                <VCardText>
                    <VForm @submit.prevent ref="refForm">
                        <VRow>
                            <!-- Username -->
                            <VCol cols="12">
                                <VTextField v-model="form.username" autofocus label="Username"
                                    :rules="[requiredValidator]" />

                                <!-- email -->
                                <VTextField v-model="form.email" label="Email" type="email" class="my-4"
                                    :rules="[requiredValidator, emailValidator]" />

                                <!-- password -->
                                <VTextField v-model="form.password" label="Password"
                                    :type="isPasswordVisible ? 'text' : 'password'"
                                    :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                                    @click:append-inner="isPasswordVisible = !isPasswordVisible"
                                    :rules="[requiredValidator, lengthValidator(form.password, 8)]" />

                                <VBtn class="mt-4" block type="submit" @click="register()">
                                    <span v-if="!isLoading">Sign Up</span>
                                    <VProgressCircular v-if="isLoading" indeterminate color="success" :size="25" />
                                </VBtn>
                            </VCol>

                            <!-- login instead -->
                            <VCol cols="12" class="d-flex justify-center flex-column flex-sm-row text-center text-base">
                                <span>Already have an account?</span>
                                <RouterLink class="text-primary ms-2" :to="{ name: 'login' }">
                                    Sign in instead
                                </RouterLink>
                            </VCol>

                        </VRow>
                    </VForm>
                </VCardText>
            </VCard>
        </div>
    </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>

<route lang="yaml">
    meta:
      layout: blank
 </route>