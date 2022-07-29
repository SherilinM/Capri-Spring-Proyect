import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: 'enum'
})

export class EnumPipe implements PipeTransform {

    transform(value: string) {
        const regex = /_/g
        let newString: string = value.replace(regex, " ")
        return newString
    }
    
}