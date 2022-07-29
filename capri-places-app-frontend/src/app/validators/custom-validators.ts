import { invalid } from "@angular/compiler/src/render3/view/util";
import { AbstractControl, ControlContainer, ValidationErrors, ValidatorFn } from "@angular/forms";

export class CustomValidators{

    static ageValidator(min:number, max:number): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            if(value < min || value > max){
                return { invalidAge:true};
            }
            return null;
        }
    }

    static checkPassword(control: AbstractControl): ValidationErrors | null {
        if (control.get('password')?.value !== control.get('passwordConfirmation')?.value){
            return {differentPassword:true}
        }
        return null;
    }

    static nameValidator(control: AbstractControl): ValidationErrors | null {
        const value = control.value;
        const regex = /\d/;
        if (regex.test(value)){
            return {invalidName:true}
        }
        return null;
    }


static urlValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;
    try {
        const url = new URL(value);
        return null;
  } catch (error){
      return {invalidUrl:true}
  }
}

}
